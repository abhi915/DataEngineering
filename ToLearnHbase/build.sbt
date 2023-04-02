import sbt._
import Keys._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.16"

lazy val root = (project in file("."))
  .settings(
    name := "ToLearnHbase"
  )

lazy val hbaseVersion = "2.1.10"
// Forced by Databricks
lazy val nettyVersion = "4.1.82.Final"


addCommandAlias("testFast", "testOnly -- -l org.scalatest.tags.Slow")

lazy val commonDependencies = Seq("org.scalatest" %% "scalatest" % "3.1.0" % Test)

lazy val hbaseClientDependencies = Seq(
  "org.apache.hbase" % "hbase-client" % hbaseVersion excludeAll (
    "org.slf4j" % "slf4j-log4j12"),
  "org.apache.hbase" % "hbase-common" % hbaseVersion)

  lazy val `hbase-client` = (project in file("hbase-client"))
  .settings(
    name := "hbase-client",
    libraryDependencies ++= commonDependencies ++ hbaseClientDependencies)
