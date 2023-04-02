package com.learn.core.hbase

case class Config(zookeeperQuorum:String = "192.168.1.41" , clientPort:String = "2181", znodeParent:String =  "/hbase", distributed:Boolean = false) {



  def getHbaseProperties: Map[String,String] = {
    val baseProperties = Map(
      "hbase.zookeeper.quorum" -> zookeeperQuorum,
      "hbase.zookeeper.property.clientPort" -> clientPort,
      "zookeeper.znode.parent" -> znodeParent,
      "hbase.cluster.distributed" -> distributed.toString)

    baseProperties
  }



}
