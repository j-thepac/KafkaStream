package kafkastream
import org.apache.spark.sql.{Row, SparkSession}




trait SparkInvoke {


  val spark=SparkSession
    .builder()
    .master("local")
    .appName("test")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  //  import spark.implicits._
}
