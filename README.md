## Setup
From the [Spring Cloud Data low](http://cloud.spring.io/spring-cloud-dataflow/) website.

### Downloads
```bash
wget http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server-local/1.1.4.RELEASE/spring-cloud-dataflow-server-local-1.1.4.RELEASE.jar
wget http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/1.1.4.RELEASE/spring-cloud-dataflow-shell-1.1.4.RELEASE.jar
```

### Brew Setup
Installs the project dependencies
```bash
brew bundle
```

### Brew startup
```bash
brew start services zookeeper
brew start services kafka
```

### Start Local Server
```bash
java -jar spring-cloud-dataflow-server-local-1.1.4.RELEASE.jar
```

### Start CLI shell
```bash
java -jar spring-cloud-dataflow-shell-1.1.4.RELEASE.jar
```

## Compile
From the project directory
```bash
mvn clean install -T2C
```

### Install apps
```bash
app register --name ipampas-filter --type processor --uri file://Users/young/Desktop/ipampas/ipampas-spring-cloud-data-flow/ipampas-filter/target/ipampas-filter-0.0.1-SNAPSHOT.jar
app register --name ipampas-processor --type processor --uri file://Users/young/Desktop/ipampas/ipampas-spring-cloud-data-flow/ipampas-processor/target/ipampas-processor-0.0.1-SNAPSHOT.jar
app register --name ipampas-sink --type sink --uri file://Users/young/Desktop/ipampas/ipampas-spring-cloud-data-flow/ipampas-sink/target/ipampas-sink-0.0.1-SNAPSHOT.jar
        


### Create Stream
```bash
stream create --name ipampas-converter --definition "read: file --directory=/Users/young/Downloads/inputs --filename-pattern=*.txt --mode=lines | filter: ipampas-filter | processor: ipampas-processor | sink: ipampas-sink"
```

### Deploy Stream
```bash
stream deploy --name ipampas-converter
```

## Redeploy
```bash
stream undeploy --name ipampas-converter
stream deploy --name ipampas-converter
```

## Documentation

### Input data
This file is also available in `docs/input.json`
```json
{"date":"2017-01-01","value":30,"unit":"CELSIUS"}
{"date":"2017-01-02","value":40,"unit":"CELSIUS"}
{"date":"2017-02-01","value":20,"unit":"FAHRENHEIT"}
{"date":"2017-02-02","value":50,"unit":"FAHRENHEIT"}
```

### High Level Flow
![High Level Flow](/docs/high-level-flow.png?raw=true)
