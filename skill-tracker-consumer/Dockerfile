FROM openjdk:11-jdk-slim
MAINTAINER Taner Diler <taner.diler@gmail.com>
ADD target/skill-tracker-consumer-1.0.jar skill-tracker-consumer-1.0.jar
ENTRYPOINT ["java", "-jar", "/skill-tracker-consumer-1.0.jar"]
EXPOSE 5001