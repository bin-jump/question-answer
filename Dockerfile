FROM openjdk:8-jdk-alpine

ARG JAR_FILE=./presentation/target/*.jar
COPY ${JAR_FILE} app.jar
COPY docker/wait_for_elasticsearch.sh wait_for_elasticsearch.sh

RUN chmod +x wait_for_elasticsearch.sh
CMD ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]