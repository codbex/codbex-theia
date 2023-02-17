# codbex-theia

Theia Edition contains only the Terminal over HTTP standard components.

It is good for getting termical access to the target environment such as Kubernetes.


#### Docker

```
docker run --name codbex-theia --rm -p 8080:8080 ghcr.io/codbex/codbex-theia:latest
```

#### Build

```
mvn clean install
```
	
#### Run

```
java -jar application/target/codbex-theia-application-1.0.0-SNAPSHOT.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-theia-application-1.0.0-SNAPSHOT.jar
```
	
#### Web

```
http://localhost:8080
```

#### REST API

```
http://localhost:8080/swagger-ui/index.html
```
