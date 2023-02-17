# codbex-theia

Theia Edition contains only the Terminal over HTTP standard components.

It is good for getting termical access to the target environment such as Kubernetes.

#### Build

	mvn clean install
	
#### Run

	java -jar application/target/codbex-theia-application-0.1.0-SNAPSHOT.jar

#### Debug

	java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-theia-application-0.1.0-SNAPSHOT.jar
	
#### Web

	http://localhost:8080/

#### REST API

	http://localhost:8080/swagger-ui/index.html


