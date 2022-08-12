FROM openjdk:11-jdk-slim
MAINTAINER Taner Diler <taner.diler@gmail.com>
ADD target/skill-tracker-api-gateway-1.0.jar skill-tracker-api-gateway-1.0.jar
ENTRYPOINT ["java", "-jar", "/skill-tracker-api-gateway-1.0.jar"]
EXPOSE 4000