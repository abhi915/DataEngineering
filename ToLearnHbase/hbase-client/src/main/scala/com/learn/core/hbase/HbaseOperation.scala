package com.learn.core.hbase

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, Put, Table}
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, TableName}
import java.time.Instant

import scala.util.{Failure, Success, Try}

class HbaseOperation(config : Configuration) {

  val conn = ConnectionFactory.createConnection(config)

  def closeConn = conn.close()


  def createTable(tableName:String) = {

    val result = Try {
    val admin = conn.getAdmin
    val hTable = TableName.valueOf(tableName.getBytes())

    val htableDesc = new HTableDescriptor(hTable)
    htableDesc.addFamily(new HColumnDescriptor("personal"))
    htableDesc.addFamily(new HColumnDescriptor("professional"))
    admin.createTable(htableDesc);
    println("table is created")
    }
    result match {
      case Failure(ex) => println(ex.getMessage); //closeConn
      case Success(_) => println("code executed successfully");//closeConn
    }

  }

  def insertData(tableName: String,row : Array[Byte], value:String, columnFamily: String,columnQualifier:String): Unit = {

    val result = Try {
       val hTableName: TableName = TableName.valueOf(tableName)
       val table: Table = conn.getTable(hTableName)
       val p = new Put(row)
       p.addColumn(columnFamily.getBytes(),columnQualifier.getBytes(),Instant.now().getEpochSecond,value.getBytes())
       table.put(p)
    }
    result match {
      case Failure(ex) => println(ex.getMessage); //closeConn
      case Success(_) => println("code executed successfully"); //closeConn
    }
  }

  def readData(tableName: String, row: Array[Byte], columnFamily:String, columnQualifier:String )= {

    val result = Try {

      val admin = conn.getAdmin
      val hTableName: TableName = TableName.valueOf(tableName)
      val table: Table = conn.getTable(hTableName)

      val g = new Get(row)
      val r = table.get(g)


      println("table descriptor details started")
      println(String.valueOf(r))
      println(">>" +  new String(r.getValue(columnFamily.getBytes,columnQualifier.getBytes)))

    }
    result match {
      case Failure(ex) => println(ex.getMessage); //closeConn
      case Success(_) => println("code executed successfully");//closeConn
    }
  }




}
