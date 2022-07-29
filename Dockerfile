FROM openjdk:17-jdk-alpine3.12
ADD *.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]
EXPOSE 8080