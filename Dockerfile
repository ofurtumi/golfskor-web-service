FROM maven:3.8.1-openjdk-17-slim
VOLUME /tmp
COPY .env .env
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package
CMD ["java","-jar","/target/Golfskor-2.0.0.jar"]
