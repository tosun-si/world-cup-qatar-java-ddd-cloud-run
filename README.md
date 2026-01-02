# world-cup-qatar-java-ddd-cloud-run

Real World use case with an application using DDD and Clean Architecture, deployed as a Serverless application on 
Cloud Run (GCP) with GraalVM native compilation for improved cold start performance.

## Use Case

This application processes FIFA World Cup Qatar 2022 team player statistics:
1. **Read** raw player statistics from a data source (Firestore or AlloyDB)
2. **Transform** raw data into domain projections (aggregated team statistics)
3. **Store** the projections in BigQuery for querying

## DDD and Clean Architecture

### Project Structure

```
src/main/java/fr/groupbees/
├── domain/                    # Domain Layer - Business logic
│   ├── model/                 # Domain objects (TeamPlayerStats, TeamPlayerStatsRaw)
│   └── service/               # Domain services (TeamPlayerStatsService)
├── application/               # Application Layer - Use cases
│   ├── usecase/               # Application services (GetTeamPlayerStatsRawUseCase, etc.)
│   └── port/                  # Ports (interfaces for external dependencies)
└── infrastructure/            # Infrastructure Layer - External concerns
    ├── web/                   # REST controllers
    ├── firestore/             # Firestore adapter
    ├── alloydb/               # AlloyDB adapter
    ├── bigquery/              # BigQuery adapter
    ├── inmemory/              # In-memory adapter (for tests)
    └── config/                # Spring configuration
```

### DDD Concepts Used

| Concept | Implementation | Purpose |
|---------|---------------|---------|
| **Layered Architecture** | `domain/`, `application/`, `infrastructure/` | Clear separation of concerns |
| **Hexagonal Architecture** | Ports in `application/port/`, Adapters in `infrastructure/` | Decouple domain from external systems |
| **Domain Service** | `TeamPlayerStatsService` | Stateless domain logic (raw to domain transformation) |
| **Use Cases** | `GetTeamPlayerStatsRawUseCase`, `SaveTeamPlayerStatsDomainUseCase` | Orchestrate domain operations |
| **Repository Pattern** | `TeamPlayerStatsRawRepository`, `TeamPlayerStatsRepository` | Abstract data access |
| **Value Object** | `TeamPlayerStatsRaw` | Immutable data from external source |
| **Projection** | `TeamPlayerStats` | Computed domain object for querying |

### Architecture Decisions

- **No Aggregates**: This domain is transformation/query-focused, not write-heavy with complex invariants. Aggregates are not force-fitted where they don't add value.
- **Adapter Flexibility**: The hexagonal architecture allows swapping data sources (Firestore, AlloyDB, In-memory) without changing domain or application logic.
- **Testability**: Tests use in-memory adapters, running without external dependencies.

### Business Rules in Domain Model

The `TeamPlayerStats` projection contains encapsulated business logic:

| Method | Description | Return Type |
|--------|-------------|-------------|
| `getPerformanceRating()` | Calculates team performance score (0-100) based on weighted offensive and defensive stats | `Integer` |
| `getTeamStyle()` | Classifies team playing style based on offensive vs defensive stats ratio | `OFFENSIVE`, `BALANCED`, `DEFENSIVE` |
| `getGoalkeeperReliability()` | Evaluates goalkeeper based on save percentage | `ELITE`, `RELIABLE`, `AVERAGE` |
| `getStarPlayersStats()` | Returns star players (appearing in multiple categories) with their category details | `StarPlayersStats` |

Example output for `getStarPlayersStats()`:
```
StarPlayersStats(
  starPlayers: [
    StarPlayerDetail(playerName: "Lionel Messi", categories: ["Top Scorers", "Best Passers"]),
    StarPlayerDetail(playerName: "N'Golo Kante", categories: ["Most Appearances", "Most Duels Won", "Most Successful Tackles"])
  ],
  totalCount: 2
)
```

These methods demonstrate domain logic encapsulated within the domain model, following DDD principles.

## Run locally

### With standard Spring Boot and Java

Build the application:

```bash
./mvnw clean package
```

Run the application:

```bash
./mvnw spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/world-cup-qatar-java-ddd-cloud-run-0.0.1-SNAPSHOT.jar
```

### With GraalVM native image

Build the native image (requires GraalVM installed):

```bash
./mvnw -Pnative native:compile
```

Run the native executable:

```bash
./target/world-cup-qatar-java-ddd-cloud-run
```

## AlloyDB firewall rule

```bash
gcloud compute firewall-rules create allow-alloydb-ingress-serverless \
    --network default \
    --allow tcp:5432 \
    --source-ranges 10.8.0.0/28 \
    --description "Allow VPC connector traffic to AlloyDB for Serverless Components"
```

## Deployment Strategy: Standard JVM vs GraalVM Native

This application can be deployed with two approaches to compare their characteristics:

| Aspect | Standard JVM | GraalVM Native |
|--------|--------------|----------------|
| **Cold Start** | Slower (JVM startup + class loading) | Much faster (native executable) |
| **Memory Usage** | Higher (JVM overhead) | Lower (no JVM) |
| **Build Time** | Fast | Slower (AOT compilation) |
| **Peak Performance** | Better (JIT optimizations) | Good (but no runtime optimizations) |
| **Image Size** | Larger (~200-300MB) | Smaller (~50-100MB) |

### Why GraalVM for Serverless?

In serverless environments like Cloud Run, **cold starts** are critical because:
- Instances scale to zero when idle
- New instances must start quickly to handle incoming requests
- Users experience latency during cold starts

GraalVM native compilation addresses this by:
1. **Ahead-of-Time (AOT) compilation**: Code is compiled to native machine code at build time, not runtime
2. **No JVM startup**: The executable runs directly without JVM initialization
3. **Reduced memory footprint**: No need to load JVM classes and metadata
4. **Instant startup**: Application is ready in milliseconds instead of seconds

### Spring AOT: Simplifying GraalVM with Spring Boot

GraalVM native-image requires all classes and methods to be known at build time (closed-world assumption). This is challenging for Spring Boot, which heavily uses reflection, dynamic proxies, and runtime configuration.

**Spring AOT (Ahead-of-Time) processing** solves this by analyzing the application at build time and generating:
- **Reflection hints**: Classes that need reflection access
- **Proxy hints**: Interfaces requiring dynamic proxies
- **Resource hints**: Files to include in the native image
- **Serialization hints**: Classes needing serialization support

These hints are generated in `META-INF/native-image/` and tell GraalVM exactly what to include.

To enable Spring AOT, simply use the `native` Maven profile:
```bash
./mvnw -Pnative native:compile
```

This dramatically simplifies GraalVM adoption - no manual configuration of reflection or resources needed.

## Deploy the app in Cloud Run with the standard way

Deploy the image

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/standard/deploy-cloud-run-image-standard.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_STANDARD="$SERVICE_NAME_STANDARD",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

Deploy the Service

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/standard/deploy-cloud-run-service-standard.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_STANDARD="$SERVICE_NAME_STANDARD",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

With Docker 

Build 

```bash
docker build -f cicd/standard/Dockerfile -t "$SERVICE_NAME_STANDARD" .
```

Run 

```bash
docker run -it \
    -p 8080:8080 \
    -e PROJECT_ID=$PROJECT_ID \
    -e LOCATION=$LOCATION \
    -e ALLOY_DB_CLUSTER_NAME="$ALLOY_DB_CLUSTER_NAME"  \
    -e ALLOY_DB_INSTANCE_NAME="$ALLOY_DB_INSTANCE_NAME" \
    -e ALLOY_DB_USERNAME="$ALLOY_DB_USERNAME" \
    -e ALLOY_DB_PASSWORD="$ALLOY_DB_PASSWORD" \
    -v $HOME/.config/gcloud:/root/.config/gcloud \
    $SERVICE_NAME_STANDARD
```

## Create the AlloyDB instance with Terraform

```bash
gcloud builds submit \
  --project=$PROJECT_ID \
  --region=$LOCATION \
  --config cicd/create-alloydb-cluster-terraform-apply.yaml \
  --substitutions _TF_STATE_BUCKET=$TF_STATE_BUCKET,_TF_STATE_PREFIX=$TF_STATE_PREFIX,_GOOGLE_PROVIDER_VERSION=$GOOGLE_PROVIDER_VERSION \
  --verbosity="debug" .
```

## Deploy the app in Cloud Run with native compilation and GraalVM

### Why a Custom Dockerfile?

This project uses a custom Dockerfile instead of Spring Boot Buildpacks for native image builds. This approach provides several advantages:

| Benefit | Description |
|---------|-------------|
| **Docker layer caching** | Separates dependency download from compilation, optimizing build time with Cloud Build |
| **Multi-stage builds** | Produces smaller final images by excluding build tools and intermediate files |
| **Improved security** | Minimal final image with only the native executable, reducing attack surface |
| **Faster cold starts** | Smaller images load faster when Cloud Run scales up new instances |

#### Multi-Stage Build Strategy

```dockerfile
# Stage 1: Build (large image with GraalVM, Maven, dependencies)
FROM ghcr.io/graalvm/native-image:... AS builder
# Download dependencies (cached layer)
# Compile native image

# Stage 2: Runtime (minimal image with only the executable)
FROM debian:bookworm-slim
COPY --from=builder /app/target/myapp /app
# Final image: ~50-100MB instead of ~1GB+
```

As an alternative, Spring Boot Buildpacks can build a native image with:
```bash
./mvnw spring-boot:build-image -Pnative -Dspring-boot.build-image.imageName=myapp
```

Deploy the Image

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/native-graalvm/deploy-cloud-run-image-graalvm.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_NATIVE_GRAAL_VM="$SERVICE_NAME_NATIVE_GRAAL_VM",_IMAGE_TAG="$IMAGE_TAG" \
    --verbosity="debug" .
```

Deploy the Service

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/native-graalvm/deploy-cloud-run-service-graalvm.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_NATIVE_GRAAL_VM="$SERVICE_NAME_NATIVE_GRAAL_VM",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

With Docker

Build (requires Docker Desktop memory set to 12-16GB in Settings → Resources)

```bash
docker build -f cicd/native-graalvm/Dockerfile -t $SERVICE_NAME_NATIVE_GRAAL_VM .
```

Run

```bash
docker run -it \
    -p 8080:8080 \
    -e PROJECT_ID=$PROJECT_ID \
    -e LOCATION=$LOCATION \
    -e ALLOY_DB_CLUSTER_NAME="$ALLOY_DB_CLUSTER_NAME"  \
    -e ALLOY_DB_INSTANCE_NAME="$ALLOY_DB_INSTANCE_NAME" \
    -e ALLOY_DB_USERNAME="$ALLOY_DB_USERNAME" \
    -e ALLOY_DB_PASSWORD="$ALLOY_DB_PASSWORD" \
    -v $HOME/.config/gcloud:/root/.config/gcloud \
    $SERVICE_NAME_NATIVE_GRAAL_VM
```