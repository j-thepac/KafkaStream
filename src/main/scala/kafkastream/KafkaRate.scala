package kafkastream

import kafkastream.SparkInvoke
import org.apache.spark.sql.functions._

object KafkaRate extends App with SparkInvoke {
  val initDF = spark
  .readStream
  .format ("rate") //Rate source will auto-generate data which we will then print onto a console.
  .option ("rowsPerSecond", 1)
  .load ()


  println ("Streaming DataFrame : " + initDF.isStreaming)
  val resultDF = initDF.withColumn ("result", col ("value") + lit (1) )
  resultDF
  .writeStream
  .outputMode ("append")
  .option ("truncate", false)
  .format ("console")
  .start ()
  .awaitTermination ()
}
