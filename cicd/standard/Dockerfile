FROM maven:3-eclipse-temurin-23-alpine as builder

WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:resolve dependency:resolve-plugins -B

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=builder /app/target/world-cup-qatar-java-ddd-cloud-run-*.jar /app/world-cup-qatar-java-ddd-cloud-run.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/world-cup-qatar-java-ddd-cloud-run.jar"]
