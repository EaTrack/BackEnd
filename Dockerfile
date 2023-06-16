FROM maven:3-eclipse-temurin-17-alpine

WORKDIR /Eatrack
COPY . .

RUN mvn clean install -DskipTests

CMD mvn spring-boot:run