name := """play-blog-sample"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "org.postgresql" % "postgresql" % "9.4-1204-jdbc42",
  //Testing Frameworks
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.2.0" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


//To fork all test tasks (test, test-only, and test-quick) and run tasks (run, run-main, test:run, and test:run-main),
fork := true

/**
  * DEVELOPMENT JAVA OPTIONS
  */
// Since run is forked (different tasks are being down in different jvm processes, we need to specify options
// in this way
javaOptions in run ++= Seq(
  "-Dconfig.resource=devtest-conf/devtest.conf",
  "-Dhttp.port=1234"
)