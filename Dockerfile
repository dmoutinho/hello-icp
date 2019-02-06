FROM maven
COPY . /usr/src/myapp
RUN cd /usr/src/myapp/ && mvn clean install
WORKDIR /usr/src/myapp/target
CMD java -Dserver.port=9080 -jar hello-icp-0.0.1-SNAPSHOT.jar
EXPOSE 9080
