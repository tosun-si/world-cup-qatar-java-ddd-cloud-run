FROM maven:3-eclipse-temurin-23-alpine as builder

WORKDIR /app

COPY pom.xml ./

# Copy the cached .m2 directory from Cloud Build or local machine
#ARG CACHE_DIR=/workspace/.m2
#COPY $CACHE_DIR /root/.m2

RUN mvn dependency:resolve dependency:resolve-plugins -B

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:23-jre
#FROM eclipse-temurin:23_37-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/world-cup-qatar-java-ddd-cloud-run-*.jar /app/world-cup-qatar-java-ddd-cloud-run.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/world-cup-qatar-java-ddd-cloud-run.jar"]
