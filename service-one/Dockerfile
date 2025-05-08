FROM maven:3.6-openjdk-17 AS maven_tool
WORKDIR /tmp/
COPY . /tmp/
RUN mvn -B clean package
FROM openjdk:17-jdk
EXPOSE 8080
WORKDIR /app
COPY --from=maven_tool /tmp/target/*.jar /app/spring-boot-application.jar
ENTRYPOINT [ "java","-Xmx256m", "-Xss32m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/spring-boot-application.jar" ]