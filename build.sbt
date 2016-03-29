import com.github.play2war.plugin._

name := "table4you"

version := "1.0-SNAPSHOT"
Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1207.jre7"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.38"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"
libraryDependencies += "org.glassfish.jersey.media" % "jersey-media-json-jackson" % "2.22.1"
libraryDependencies += "joda-time" % "joda-time" % "2.9.2"
libraryDependencies += "commons-codec" % "commons-codec" % "1.10"
libraryDependencies += "commons-validator" % "commons-validator" % "1.5.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.5"
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.7.1"
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.10.62"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
<<<<<<< HEAD
libraryDependencies += "org.apache.tika" % "tika-parsers" % "1.11" % "test" intransitive()



=======
libraryDependencies += "org.apache.tika" % "tika-core" % "1.12"
libraryDependencies += "org.jboss.resteasy" % "resteasy-jaxrs" % "3.0.16.Final"
>>>>>>> e43d9073ddef391b3fcb3e0fe1af1c7c23946db8

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

jacoco.settings
parallelExecution in jacoco.Config := false
jacoco.excludes        in jacoco.Config := Seq( "*Routes*", "controllers*routes*", "controllers*Reverse*", "controllers*javascript*", "controller*ref*")

//sources in (Compile, doc) <<= sources in (Compile, doc) map { _.filterNot(_.getName endsWith ".scala") }



