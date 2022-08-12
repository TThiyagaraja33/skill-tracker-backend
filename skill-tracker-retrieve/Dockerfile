FROM openjdk:11-jdk-slim
MAINTAINER Taner Diler <taner.diler@gmail.com>
ADD target/skill-tracker-retrieve-1.0.jar skill-tracker-retrieve-1.0.jar
ENTRYPOINT ["java", "-jar", "/skill-tracker-retrieve-1.0.jar"]
EXPOSE 5002