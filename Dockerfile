FROM adoptopenjdk/openjdk11:alpine-jre AS builder

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} item.jar

ENTRYPOINT ["java","-jar","/item.jar"]
