import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

organization := "com.sassunt"

name := "giStalker"

scalaVersion := "2.9.1"

version := "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-filter" % "0.5.3",
   "net.databinder" %% "unfiltered-jetty" % "0.5.3",
   "net.databinder" %% "dispatch-http" % "0.8.7",
   "net.databinder" %% "dispatch-http-json" % "0.8.7",
   "net.databinder" %% "dispatch-json" % "0.8.7",
   "joda-time" % "joda-time" % "2.0",
   "org.joda" % "joda-convert" % "1.2"
)

resolvers ++= Nil
