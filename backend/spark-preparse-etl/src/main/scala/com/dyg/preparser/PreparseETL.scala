package com.dyg.preparser

import org.apache.spark.sql.{Encoders, SaveMode, SparkSession}

object PreparseETL {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("PreparseETL")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    val rawdateInputPath = spark.conf.get("spark.traffic.analysis.rawdata.input",
      "hdfs://master:9999/user/hadoop-dyg/traffic-analysis/rawlog/20180615")

    val numPartitions = spark.conf.get("spark.traffic.analysis.rawdata.numPartitions","2").toInt


    val preParsedLogRDD = spark.sparkContext.textFile(rawdateInputPath).flatMap(line => Option(WebLogPreParser.parse(line)))

    val preParsedLogDS = spark.createDataset(preParsedLogRDD)(Encoders.bean(classOf[PreParsedLog]))

    preParsedLogDS.coalesce(numPartitions)
      .write
      .mode(SaveMode.Append)
      .partitionBy("year","month","day")
      .saveAsTable("rawdata.web")

    spark.stop()

  }
}
