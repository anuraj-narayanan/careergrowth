FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mock.jar
ENTRYPOINT ["java","-jar","/mock.jar"]
