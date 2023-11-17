FROM maven:3.8.1-openjdk-17-slim
COPY .env .env
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package
CMD ["java","-jar","/target/Golfskor-1.0.0.jar"]
