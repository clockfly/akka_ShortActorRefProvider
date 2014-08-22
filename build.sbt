name := "akka_alias_actorref_provider"

version := "0.1-SNAPSHOT"

organization := "com.github.clockfly"

scalaVersion := "2.10.4"


resolvers ++= Seq(
  "maven-repo" at "http://repo.maven.apache.org/maven2",
  "maven1-repo" at "http://repo1.maven.org/maven2",
  "apache-repo" at "https://repository.apache.org/content/repositories/releases",
  "jboss-repo" at "https://repository.jboss.org/nexus/content/repositories/releases",
  "mqtt-repo" at "https://repo.eclipse.org/content/repositories/paho-releases",
  "cloudera-repo" at "https://repository.cloudera.com/artifactory/cloudera-repos",
  "mapr-repo" at "http://repository.mapr.com/maven",
  "spring-releases" at "http://repo.spring.io/libs-release",
  "sonatype" at "https://oss.sonatype.org/content/repositories/releases"
)

parallelExecution in Test := false


val akkaVersion = "2.3.4"
val slf4jVersion = "1.7.5"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
  "org.slf4j" % "jul-to-slf4j" % slf4jVersion,
  "org.slf4j" % "jcl-over-slf4j" % slf4jVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-agent" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
)
