package com.learn.core.hbase

import org.apache.hadoop.hbase.HBaseConfiguration


object ScalaHBaseExample extends App  {

  val config  = HBaseConfiguration.create()

  Config().getHbaseProperties.foreach {
    case (key, value) => config.set(key, value)
  }


  val hbaseOp: HbaseOperation = new HbaseOperation((config))

  // hbase op - create table  --- create operation
  //hbaseOp.createTable("emp")

  // hbase op write data      --- put operation
  //hbaseOp.insertData("emp","row1".getBytes,"abhishek","personal","name")

  // hbase op  read data      --- get operation
  hbaseOp.readData("emp", "row1".getBytes(), "personal","name")

  // close connection after performing all operation
  hbaseOp.closeConn


}
