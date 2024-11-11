FROM ghcr.io/graalvm/native-image-community:23 as builder

WORKDIR /app

ENV MAVEN_VERSION=3.9.9
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Install wget and unzip
RUN mkdir -p ${MAVEN_HOME} && \
    curl -L -o apache-maven-${MAVEN_VERSION}-bin.tar.gz https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    echo "Download completed, now extracting..." && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C ${MAVEN_HOME} --strip-components=1 && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

RUN mkdir -p /root/.m2

COPY pom.xml ./

RUN mvn dependency:resolve dependency:resolve-plugins -B

# Copy the cached .m2 directory from Cloud Build or local machine
#ARG CACHE_DIR=/workspace/.m2
#COPY $CACHE_DIR /root/.m2

COPY src ./src
COPY .mvn .mvn
COPY mvnw .

RUN ./mvnw native:compile -Pnative -DskipTests

FROM container-registry.oracle.com/os/oraclelinux:9-slim
WORKDIR /app

COPY --from=builder /app/target/world-cup-qatar-java-ddd-cloud-run /app/world-cup-qatar-java-ddd-cloud-run

EXPOSE 8080

ENTRYPOINT ["/app/world-cup-qatar-java-ddd-cloud-run"]
