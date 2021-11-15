load("@io_bazel_rules_scala//scala:scala.bzl", "scala_binary","scala_library")
package(default_visibility = ["//visibility:public"])



scala_binary(
    name="kafkarate",
    srcs=["KafkaRate.scala"
          ,"SparkInvoke.scala"
          ],
    main_class="kafkastream.KafkaRate",
    deps = ["kafka"
            ,"spark"
            ],
)


scala_binary(
    name="kafkacloud",
    srcs=["KafkaCloud.scala"
          ,"SparkInvoke.scala"
          ],
    main_class="kafkastream.KafkaCloud",
    deps = ["kafka"
            ,"spark"
            ],
)
scala_binary(
    name="kafkafolder",
    srcs=["KafkaFolder.scala"
          ,"SparkInvoke.scala"
          ],
    main_class="kafkastream.KafkaFolder",
    deps = ["kafka"
            ,"spark"
            ],
)

scala_binary(
    name="kafkasocket",
    srcs=["KafkaSocket.scala"
          ,"SparkInvoke.scala"
          ],
    main_class="kafkastream.KafkaSocket",
    deps = ["kafka"
            ,"spark"
            ],
)

scala_library(
    name = "sparkinvoke",
    srcs = ["SparkInvoke.scala"],
    visibility = ["//visibility:public"],
    deps = ["spark"],

)


java_library(
    name = "kafka",
    exports = [
        "@maven//:org_apache_kafka_kafka_clients_2_7_0",
        "@maven//:org_apache_spark_spark_sql_kafka_0_10_2_12"
        ],
)

java_library(
    name = "spark",
    exports = [
        "@maven//:org_apache_spark_spark_core_2_12",
        "@maven//:org_apache_spark_spark_sql_2_12",
        "@maven//:org_apache_spark_spark_unsafe_2_12",
        "@maven//:org_apache_spark_spark_tags_2_12",
        "@maven//:org_apache_spark_spark_catalyst_2_12",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        ]
        )

java_library(
    name = "json4s",
    exports = [
        "@maven//:org_json4s_json4s_jackson_2_12",
        "@maven//:org_json4s_json4s_core_2_12",
        "@maven//:org_json4s_json4s_ast_2_12",
        "@maven//:org_json4s_json4s_scalap_2_12",
        ]
    )

