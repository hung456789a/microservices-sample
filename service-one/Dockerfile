FROM maven:3.6-openjdk-17 AS maven_tool
WORKDIR /tmp/
COPY . /tmp/
RUN mvn -B clean package
FROM openjdk:17-jdk
EXPOSE 8080
COPY --from=maven_tool /tmp/target/*.jar /app/spring-boot-application.jar
WORKDIR /app
ENTRYPOINT [ "java", "-jar", "/app/spring-boot-application.jar" ]