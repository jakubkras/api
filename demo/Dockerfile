FROM openjdk:17-jdk-slim

COPY target/demo-0.0.1-SNAPSHOT.jar app/demo.jar

WORKDIR /app

EXPOSE 8080

CMD ["java","-jar","demo.jar"]