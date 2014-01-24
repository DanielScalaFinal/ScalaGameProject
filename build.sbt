import sbt._


scalaVersion := "2.10.2"

name := "Mahjong"

version := "1.0"

unmanagedResourceDirectories in Compile <+= baseDirectory( _ / "src" )

libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-swing" % "2.10.2",
	"org.scala-tools.testing" % "specs_2.10" % "1.6.9",
	"org.scala-lang" % "scala-actors" % "2.10.2",
	"junit" % "junit" % "4.11",
	"org.hamcrest" % "hamcrest-core" % "1.3",	
	"org.specs2" % "specs2_2.10" % "2.3-scalaz-7.1.0-M3",
	"org.scalatest" % "scalatest_2.9.0" % "1.6.1" % "test"
)

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                  "releases"  at "http://oss.sonatype.org/content/repositories/releases")

seq(ScctPlugin.instrumentSettings : _*)

testOptions in Test += Tests.Argument("junitxml", "console")

parallelExecution in Test := false