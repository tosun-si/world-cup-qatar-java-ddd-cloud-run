FROM maven:3-eclipse-temurin-23-alpine AS builder

ENV WORKDIR=/usr/local/src/app
WORKDIR $WORKDIR

COPY pom.xml ./

RUN mvn dependency:resolve dependency:resolve-plugins -B

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:23-jre

LABEL org.opencontainers.image.title="World Cup Qatar Stats API" \
      org.opencontainers.image.description="JVM image for World Cup Qatar 2022 statistics"

ENV WORKDIR=/usr/local/src/app
WORKDIR $WORKDIR

RUN adduser --disabled-password --gecos '' --home /home/appuser appuser && \
    chown -R appuser $WORKDIR

COPY --from=builder --chown=appuser:appuser $WORKDIR/target/world-cup-qatar-java-ddd-cloud-run-*.jar $WORKDIR/world-cup-qatar-java-ddd-cloud-run.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "world-cup-qatar-java-ddd-cloud-run.jar"]
