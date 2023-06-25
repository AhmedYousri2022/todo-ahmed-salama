FROM adoptopenjdk/openjdk11:alpine-jre AS builder

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} todo.jar

ENTRYPOINT ["java","-jar","/todo.jar"]
