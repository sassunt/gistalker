package com.sassunt

import unfiltered.request._
import unfiltered.response._
import util.Properties

class App(target: String, fileName: String) extends unfiltered.filter.Plan {

  def intent = {
    case GET(_) => Ok ~> {
      import Template._
      import dispatch._

      val gists = Github.using(new Github) { g =>
        val TargetURL = """\*\shttps://github.com/([^/]*)""".r
        val ls = g.gist(target, fileName).toList.flatMap {
          _.content.split("\n") collect { case TargetURL(user) => user }
        }

        g.gists(ls)
      }
      display(<div>{gists.map(g => g.embed)}</div>)
    }
  }
}

object Web {
  def main(args: Array[String]) {
    val targetID = args(1) // TODO
    val file = args(2)
    val port = Properties.envOrElse("PORT", "8080").toInt
    unfiltered.jetty.Http(port).context("/assets"){
      _.resources(new java.net.URL(getClass().getResource("/css"), "."))
    }.filter(new App(targetID, file)).run( _ =>
       unfiltered.util.Browser.open("http://localhost:%s".format(port.toString))
    )
  }
}
