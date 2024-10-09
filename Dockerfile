FROM maven:3-eclipse-temurin-23-alpine as builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:23_37-jre-alpine

COPY --from=builder /app/target/world-cup-qatar-java-ddd-cloud-run-*.jar /world-cup-qatar-java-ddd-cloud-run.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/world-cup-qatar-java-ddd-cloud-run.jar"]
