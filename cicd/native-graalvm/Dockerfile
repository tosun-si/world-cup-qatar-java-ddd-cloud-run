FROM ghcr.io/graalvm/native-image-community:23 AS builder

ENV WORKDIR=/usr/local/src/app \
    MAVEN_VERSION=3.9.9 \
    MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

WORKDIR $WORKDIR

RUN mkdir -p ${MAVEN_HOME} && \
    curl -L -o apache-maven-${MAVEN_VERSION}-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    echo "Download completed, now extracting..." && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C ${MAVEN_HOME} --strip-components=1 && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

RUN mkdir -p /root/.m2

COPY pom.xml ./

RUN mvn dependency:resolve dependency:resolve-plugins -B

COPY src ./src
COPY .mvn .mvn
COPY mvnw .

RUN ./mvnw native:compile -Pnative -DskipTests

FROM gcr.io/distroless/base-debian12:nonroot

LABEL org.opencontainers.image.title="World Cup Qatar Stats API" \
      org.opencontainers.image.description="GraalVM native image for World Cup Qatar 2022 statistics"

ENV WORKDIR=/usr/local/src/app
WORKDIR $WORKDIR

# Distroless base image doesn't include zlib, required by the native binary for compression (gzip, SSL)
COPY --from=builder /usr/lib64/libz.so.1 /usr/lib/
COPY --from=builder --chown=nonroot:nonroot $WORKDIR/target/world-cup-qatar-java-ddd-cloud-run $WORKDIR/world-cup-qatar-java-ddd-cloud-run

ENV LD_LIBRARY_PATH=/usr/lib

USER nonroot

EXPOSE 8080

ENTRYPOINT ["./world-cup-qatar-java-ddd-cloud-run"]
