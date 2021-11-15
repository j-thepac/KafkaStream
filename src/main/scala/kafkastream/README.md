# Stream
- data is continuously coming in
- Spark Streaming is a processing engine to process data in real-time from sources and output data to external storage systems.
- Two type
    - Batch 
        - Executes like normal spark job
        - Uses : spark.read and df.write
    - Streaming 
        - micro batches
        - seperate thread
        - Runs infinitly
        - df.show() command does not work. Write to console is used instead
        - Uses 
            - spark.readstream 
            - df.writestream

### Input
- Broker
    - Cloud
    - Local
- Rate
- Folder
- Socket

note:
Streaming DataFrame doesn't support the show() method.
https://stackoverflow.com/a/45092517

###  Output Sinks ( Saved to )
- Console sink -  Displays the content of the DataFrame to console
- File sink - csv, json, orc, and parquet.
- Kafka sink - Publishes data to a Kafka® topic.
- Foreach sink - Applies to each row of a DataFrame 
  - open: function to open connection
  - process: write data to the specified connection
  - close: function to close connection
- ForeachBatch sink -Applies to each micro-batch of a DataFrame

#### output mode
-   update: the rows that were updated"
-   complete: All rows will be written"
-   append: Only new rows".

## Kafka
- Kafka Stream accepts data either in String or Byte Format
- To pass it as string , u need to convert it into json format
- when you use spark.read.format("kafka") , all the data in the Dataframe has to go into a column called "value"
    
        val df:DataFrame=Seq(("1","value1"),("2","value2")).toDF("col1","col2")
        val df2 = df.select(col("col1"),to_json(struct($"*"))).toDF("key","value")


- While retrieving data 

        df.selectExpr("CAST(value AS STRING) as value")
        select(from_json(col("value"), schema).as("data"))
        .show()
        +---+--------------------+
        |key|               value|
        +---+--------------------+
        |  1|{"col1":"1","col2...|
        |  2|{"col1":"2","col2...|
        +---+--------------------+

## CloudStream

### Pre-Req:
- Make sure build.sbt is updated

        assemblyMergeStrategy in assembly := {
         case PathList("META-INF", xs @ _*) => MergeStrategy.discard
         case x => MergeStrategy.first
        }
    
- root/project/plugins.sbt is updated

        addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")
    
### Steps to Run Cloudstream:

    cd to root folder
    sbt assembly (TO create jar)
    cd target/scala-2.12/
    spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.2.0 --class KafkaStream.KafkaCloudStream scala_sample-assembly-0.1.jar