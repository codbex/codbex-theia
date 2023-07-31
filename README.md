# codbex-theia

Theia Edition contains only the Terminal over HTTP standard components.

It is good for getting shell access to the target environment such as Kubernetes.


#### Docker

```
docker pull ghcr.io/codbex/codbex-theia:latest
docker run --name codbex-theia --rm -p 80:80 ghcr.io/codbex/codbex-theia:latest
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
http://localhost
```

#### REST API

```
http://localhost/swagger-ui/index.html
```
