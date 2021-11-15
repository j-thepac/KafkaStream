package kafkastream

import org.apache.spark.sql.functions.{col, from_json, struct, to_json}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import kafkastream.SparkInvoke

object KafkaCloud extends App with SparkInvoke {
  import spark.implicits._

  val apiKey: String = sys.env.get("IBMCLOUD_PERSONAL_APIKEY") match {
    case Some(key) => key
    case _ => {
      throw new Exception("IBMCLOUD_PERSONAL_APIKEY not set in env ")
    }
  }

  val saslConfig: String = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"token\" password=\"" + apiKey + "\";"
  val commonOptions: Map[String, String] = Map(
    "kafka.bootstrap.servers" -> "broker-0-ndltb85m6rgn3xq4.kafka.svc09.us-south.eventstreams.cloud.ibm.com:9093"
    , "kafka.sasl.jaas.config" -> saslConfig
    , "kafka.sasl.mechanism" -> "PLAIN"
    , "kafka.security.protocol" -> "SASL_SSL"
  )
  val schema = StructType(Seq(
    StructField("age", IntegerType, false)
    , StructField("name", StringType, false)
  ))

  val topic = "testtopic"

  val df = Seq((3, "Alice"), (5, "Bob")).toDF("age", "name")
  val df2 = df.select(col("name"), to_json(struct($"*"))).toDF("key", "value")
  df2.selectExpr("key", "value").write.format("kafka").options(commonOptions).option("topic", topic).save()   // Write To Stream

  val resultdf = spark.read.format("kafka").options(commonOptions).option("subscribe", topic).load()   // Read Stream
  resultdf
    .selectExpr("CAST(value AS STRING) as value")
    .select(from_json(col("value"), schema).as("data"))
    .select("data.*")
    .show()

}
