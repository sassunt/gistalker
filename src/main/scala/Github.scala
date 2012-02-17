package com.sassunt

import dispatch._
import dispatch.json._
import dispatch.json.JsHttp._
import unfiltered.request._
import unfiltered.response._

class Github {
  import org.joda.time.{DateTime, DateTimeZone}
  import org.joda.time.format.ISODateTimeFormat

  private lazy val http = new Http()
  val request = :/("api.github.com").secure

  val timezone = DateTimeZone forID ("Asia/Tokyo")

  def gists(users: List[String], limit: Int = 10)(implicit ordering: Ordering[GistInfo] = Github.dateTimeOrdering) = {
    val req = (user: String) => request / "users" / user / "gists" <<? Map("per_page" -> limit.toString)
    val gists =
      for {  user <- users
             JsObject(gist) <- http(req(user) ># list)
            (JsString("html_url"), JsString(html_url)) <- gist
            (JsString("description"), JsString(description)) <- gist
            (JsString("updated_at"), JsString(updated_at)) <- gist
            (JsString("user"), JsObject(userObj)) <- gist
            (JsString("login"), JsString(userID)) <- userObj
      } yield GistInfo(url(html_url), userID, description, new DateTime(updated_at, timezone))

    gists take limit sorted
  }

  def gist(gistID: String, fileName: String) = {
    val req = request / "gists" / gistID
    val JsObject(gist) = http(req ># obj)
    (for { (JsString("description"), JsString(description)) <- gist
           (JsString("user"), JsObject(userObj)) <- gist
           (JsString("login"), JsString(userID)) <- userObj
           (JsString("files"), JsObject(filesObj)) <- gist
           (JsString(fileName), JsObject(file)) <- filesObj
           (JsString("raw_url"), JsString(raw_url)) <- file
    } yield GistRaw(req,
                    userID,
                    description,
                    fileName, http(dispatch.url(raw_url) as_str))
    ).filter{
      _.name == fileName
    } headOption
  }

  def shutdown() = http.shutdown()
}

object Github {
  def using[A <: Github, B] (g: A)(f: A => B): B = try { f(g) } finally { g.shutdown() }

  implicit val dateTimeOrdering = new Ordering[GistInfo] {
    def compare(a: GistInfo, b: GistInfo) = b.updatedTime compareTo a.updatedTime
  }
}

trait Gist {
  import scala.xml.NodeSeq
  val url: Request
  val user: String
  val dest: String
  def embed: NodeSeq
}

case class GistRaw(url: Request,
                   user: String,
                   dest: String,
                   name: String,
                   content: String

) extends Gist {
  def embed = <script src={"%s.js?file=%s" format (url.to_uri, name)}></script>
}

case class GistInfo(url: Request,
                    user: String,
                    dest: String,
                    updatedTime: org.joda.time.DateTime
) extends Gist {
  def embed = <script src={"%s.js" format url.to_uri}></script>
}
