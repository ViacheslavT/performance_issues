# performance_issues local tests projects

# Contains QueryPerformanceProblem
The project is a simple REST application, which resources documented via swagger2 framework.
Based on JMH library.
![Swagger UI](/images/Swagger_UI.png)

### Requirements

 - jdk1.8
 - maven > 3.x.x version

### Build
```sh
$ cd QueryPerformanceProblem
$ mvn clean install
```

### Run
```sh
$ mvn clean spring-boot:run
```
The H2 Service configured as a default and REST will be working on it.
To use Mongo as well in runtime the mongo database should be launched on the local machine.

### Specifics
As this project uses a several spring application containers during execution the 
# server port configuration
is randomized. After lauch applucation use the specified PORT in build console and proceed to UI application
![command line](/images/spring-app-run.cfg.png)
http://localhost:{server_port}/swagger-ui.htm
# Example -> http://localhost:53047/swagger-ui.htm

### Overview

The main aim of an application is to allow us to
compare different versions of the same query and be able to benchmark the performance of its versions.
Suppose we do have h2 service implementation and we need to optimize algoritm fro performance perspective.
 - We can optimize current algorithm
 - We can use anothe datasource
 - etc.
To do this we have to get some metricks to make a decision.

So now we have a spring-boot application with simple implementation of two kind data storages
 - MongoRepository
 - H2Repository
Their basic functionality available in
 - event-service-controller
Service to provide CRUD for events
The datasource implementation can be swapped at runtime

Now we need to provide REST api for the functionality to run performcane tests against different Services implementations

### Task
Main tests controller located in
## ...\performance_issues\QueryPerformanceProblem\event-service-rest\src\main\java\event\service\controller\EventServiceTests.java

It contains a little useful endpoints but the most important here is
 - runH2BenchMark available via /v1.0/event-service/test/runh2
 - runMongoBenchMark available via /v1.0/event-service/test/runmongo
The parameters for these resources are optional.

![Executed perf test](/images/Swagger_UI_run_service.png)

THeses endpoints run appropriate JMH instances, please see in package
## ...\performance_issues\QueryPerformanceProblem\event-service-rest\src\main\java\event\service\benchmarks
 - H2ServiceBenchMark
 - MongoServiceBenchMark
 
The JMH allow us to block any requests during execution of any BenchMark which is according to requirements.

 - exactly one of the performance tests can execute at any point of time against a database installation.

Also the functionality useful if we do have a lot of simple tests we can pass them to be handled in parallel by configuraed number of threads

 - tests against different database installations can execute in parallel. So if a user starts a test for query Q, this test can execute in parallel against databases A,B,C and collect the results in a "report"



As a result of REST service call we will get a useeful report in JSON with all required information.
## Example for MONGO
```json
[
{
    "forks": 1,
    "jvm": "C:\\Program Files\\Java\\jdk1.8.0_66\\jre\\bin\\java.exe",
    "vmName": "Java HotSpot(TM) 64-Bit Server VM",
    "measurementBatchSize": 1,
    "primaryMetric": {
      "score": 0.47206952466867164,
      "scoreUnit": "ops/ms",
      "scoreConfidence": [
        "NaN",
        "NaN"
      ],
      "rawData": [
        [
          0.47206952466867164
        ]
      ],
      "scorePercentiles": {
        "99.9": 0.47206952466867164,
        "0.0": 0.47206952466867164,
        "90.0": 0.47206952466867164,
        "99.99": 0.47206952466867164,
        "95.0": 0.47206952466867164,
        "99.9999": 0.47206952466867164,
        "50.0": 0.47206952466867164,
        "99.999": 0.47206952466867164,
        "99.0": 0.47206952466867164,
        "100.0": 0.47206952466867164
      },
      "scoreError": "NaN"
    },
    "jmhVersion": "1.21",
    "measurementTime": "100 ms",
    "jdkVersion": "1.8.0_66",
    "threads": 1,
    "measurementIterations": 1,
    "secondaryMetrics": {},
    "params": {
      "N": "2"
    },
    "benchmark": "event.service.benchmarks.MongoServiceBenchMark.addEvents",
    "mode": "thrpt",
    "jvmArgs": [
      "-Xms8G",
      "-Xmx8G"
    ],
    "vmVersion": "25.66-b17",
    "warmupTime": "10 s",
    "warmupIterations": 1,
    "warmupBatchSize": 1
  }
]
```
## Example for H2
```json
[
 {
    "forks": 1,
    "jvm": "C:\\Program Files\\Java\\jdk1.8.0_66\\jre\\bin\\java.exe",
    "vmName": "Java HotSpot(TM) 64-Bit Server VM",
    "measurementBatchSize": 1,
    "primaryMetric": {
      "score": 0.15831786320438165,
      "scoreUnit": "ops/ms",
      "scoreConfidence": [
        "NaN",
        "NaN"
      ],
      "rawData": [
        [
          0.15831786320438165
        ]
      ],
      "scorePercentiles": {
        "99.9": 0.15831786320438165,
        "0.0": 0.15831786320438165,
        "90.0": 0.15831786320438165,
        "99.99": 0.15831786320438165,
        "95.0": 0.15831786320438165,
        "99.9999": 0.15831786320438165,
        "50.0": 0.15831786320438165,
        "99.999": 0.15831786320438165,
        "99.0": 0.15831786320438165,
        "100.0": 0.15831786320438165
      },
      "scoreError": "NaN"
    },
    "jmhVersion": "1.21",
    "measurementTime": "100 ms",
    "jdkVersion": "1.8.0_66",
    "threads": 1,
    "measurementIterations": 1,
    "secondaryMetrics": {},
    "params": {
      "N": "2"
    },
    "benchmark": "event.service.benchmarks.H2ServiceBenchMark.addEvents",
    "mode": "thrpt",
    "jvmArgs": [
      "-Xms8G",
      "-Xmx8G"
    ],
    "vmVersion": "25.66-b17",
    "warmupTime": "10 s",
    "warmupIterations": 1,
    "warmupBatchSize": 1
  }
]
```

Just make a comparison
![command line](/images/res_comparison.png)
