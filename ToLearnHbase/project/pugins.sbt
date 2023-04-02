


resolvers += Resolver.sonatypeRepo("public")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

addDependencyTreePlugin

// Test Coverage & Sonar
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.3")
addSbtPlugin("com.sonar-scala" % "sbt-sonar" % "2.3.0")

// Packager for Akka Applications
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.6")

// Protocol buffers codegenerator
libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.11.11"
addSbtPlugin("com.thesamet" % "sbt-protoc" % "1.0.6")

// JMH benchmark
addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.4.3")

// scalaxb XML codegenerator
addSbtPlugin("org.scalaxb" % "sbt-scalaxb" % "1.8.3")