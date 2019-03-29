FROM maven:3.5.2
COPY target/hello-icp-0.0.1-SNAPSHOT.jar .
CMD java -Dserver.port=9080 -jar hello-icp-0.0.1-SNAPSHOT.jar
EXPOSE 9080
