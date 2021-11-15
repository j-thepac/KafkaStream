package kafkastream

import kafkastream.SparkInvoke
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object KafkaFolder extends App with SparkInvoke {
  spark.sparkContext.setLogLevel("ERROR")
  val schema = StructType(
    List(
      StructField("code", IntegerType, true),
      StructField("country", StringType, true),
      StructField("currency", StringType, true),
    )
  )
  val path:String="/Users/deepakjayaprakash/Documents/knowledge/BazelKafka/src/main/scala/kafkastream/data"
  val df = spark
    .readStream
    .schema(schema)
    .option("maxFilesPerTrigger", 1) //  only a single JSON file will be streamed at a time.
    .json(path)

  df
    .writeStream
    .format("console")
    .outputMode("append")//  "update-Only the rows that were updated","complete-All rows will be written" "append -Only new rows".
    .start()
    .awaitTermination()
}


//
//object sparkConsumer extends App {

//  val spark = SparkSession
//    .builder()
//    .appName("Spark-Kafka-Integration")
//    .master("local")
//    .getOrCreate()
//
//  val schema = StructType(Array(
//    StructField("InvoiceNo", StringType, nullable = true),
//    StructField("StockCode", StringType, nullable = true),
//    StructField("Description", StringType, nullable = true),
//    StructField("Quantity", StringType, nullable = true)
//  ))
//
//  import spark.implicits._
//  val df = spark
//    .readStream
//    .format("kafka")
//    .option("kafka.bootstrap.servers", "localhost:9092")
//    .option("subscribe", "test")
//    .option("delimiter", ";")
//    .option("header","true")
//    .option("inferSchema","true")
//    .load()
//
//  val df1 = df.selectExpr("CAST(value as STRING)", "CAST(timestamp AS TIMESTAMP)").as[(String, Timestamp)]
//              .select(from_json($"value", schema).as("data"), $"timestamp")
//              .select("data.*", "timestamp")
//
//  df1.writeStream.format("console").option("truncate","false").start().awaitTermination()
//
//}