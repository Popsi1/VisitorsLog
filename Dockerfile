# define base docker image
FROM openjdk:11
LABEL maintainer="obumneme"
ADD target/logs-0.0.1-SNAPSHOT.jar logs.jar
ENTRYPOINT ["java", "-jar", "logs.jar"]