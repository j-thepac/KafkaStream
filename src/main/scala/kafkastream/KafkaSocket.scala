package kafkastream


import kafkastream.SparkInvoke
import org.apache.spark.sql.functions._

/*
Note: Make sure you have the socket open before you start the streaming application.

Open the port 9999 on localhost(127.0.0.1) and send some data to count.
bre install netcat
Send Data to Port
     We use the netcat utility to open the port. Open a terminal and run the command below.
    nc -l -p 9090
    Start the streaming application and send data to the port.

Stopping Net Cat
    ps | grep 9999
    // kill process using below command.
    kill -9 <p_id>
*/
object KafkaSocket extends App with SparkInvoke {

  spark.sparkContext.setLogLevel("ERROR")
  val df = spark.readStream
    .format("socket")
    .option("host","localhost")
    .option("port","9090")
    .load()

  val wordsDF = df.select(explode(split(df("value")," ")).alias("word"))
  val count = wordsDF.groupBy("word").count()
  val query = count.writeStream
    .format("console")
    .outputMode("complete")
    .start()
    .awaitTermination()
}

