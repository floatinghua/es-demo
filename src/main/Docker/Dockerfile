FROM openjdk:8-jdk-alpine
ADD es-demo-0.0.1-SNAPSHOT.jar es.jar
EXPOSE 8989
#CMD java -Djava.security.egd=file:/dev/./urandom -jar invoice.jar
ENTRYPOINT ["java", "-jar", "es.jar",  ">/home/docker_works/kibana/logs/es.log"]
