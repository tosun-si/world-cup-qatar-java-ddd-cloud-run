# World Cup Qatar Stats API

Java Spring Boot API for FIFA World Cup Qatar 2022 statistics, implementing Clean Architecture with Domain-Driven Design (DDD).

## Architecture

### Clean Architecture Layers
```
Domain (innermost) → Application → Infrastructure (outermost)
```

- **Domain** (`fr.groupbees.domain`): Business logic, models, services, validation rules
- **Application** (`fr.groupbees.application`): Use cases and port interfaces
- **Infrastructure** (`fr.groupbees.infrastructure`): Adapters, configs, web controllers

### Key Patterns
- **Ports & Adapters**: Repository interfaces in application layer, implementations in infrastructure
- **Use Cases**: Single-responsibility orchestrators (Get, Save operations)
- **Rich Domain Models**: Business rules encapsulated in domain objects (TeamPlayerStats, StarPlayersStats)
- **Domain Services**: Complex transformations (TeamPlayerStatsService)

### Package Structure
```
fr.groupbees/
├── domain/
│   ├── model/          # TeamPlayerStats, TeamPlayerStatsRaw, StarPlayersStats
│   └── service/        # TeamPlayerStatsService, StarPlayersStatsBuilder
├── application/
│   ├── port/           # Repository interfaces
│   └── usecase/        # GetTeamPlayerStatsRaw, GetTeamPlayerStatsDomain, SaveTeamPlayerStatsDomain
└── infrastructure/
    ├── web/            # REST controllers
    ├── firestore/      # Firestore adapter
    ├── alloydb/        # AlloyDB/JPA adapter
    ├── bigquery/       # BigQuery adapter
    ├── inmemory/       # Test adapters
    └── config/         # Spring configurations
```

## Build & Test

```bash
# Standard build
mvn clean package

# Run tests
mvn test

# Native compilation (GraalVM)
./mvnw native:compile -Pnative -DskipTests

# Spring AOT processing (inspect native configs)
./mvnw spring-boot:process-aot -Pnative
```

## Run Locally

### Standard JVM
```bash
mvn spring-boot:run
```

### Docker (Standard)
```bash
docker build -f cicd/standard/Dockerfile -t world-cup-standard .
docker run -p 8080:8080 \
    -e PROJECT_ID=$PROJECT_ID \
    -e LOCATION=$LOCATION \
    -v "$HOME/.config/gcloud:/home/appuser/.config/gcloud:ro" \
    world-cup-standard
```

### Docker (GraalVM Native)
```bash
docker build -f cicd/native-graalvm/Dockerfile -t world-cup-graalvm .
docker run -p 8080:8080 \
    -e PROJECT_ID=$PROJECT_ID \
    -e LOCATION=$LOCATION \
    -v "$HOME/.config/gcloud:/home/nonroot/.config/gcloud:ro" \
    world-cup-graalvm
```

## Spring Profiles

| Profile | Raw Data Source | Config |
|---------|-----------------|--------|
| `firestore` (default) | Cloud Firestore | `application.properties` |
| `alloydb` | AlloyDB (PostgreSQL) | `application-alloydb.properties` |

## API Endpoints

```
GET /teams/players/stats/raw   # Raw team player statistics
GET /teams/players/stats       # Enriched domain statistics with business rules
```

## Google Cloud Services

- **Cloud Run**: Container deployment (GraalVM native or JVM)
- **Firestore**: NoSQL database for raw stats (default profile)
- **AlloyDB**: PostgreSQL for raw stats (alloydb profile)
- **BigQuery**: Data warehouse for processed domain stats

## Environment Variables

```bash
PROJECT_ID              # GCP project ID
LOCATION                # GCP region (e.g., europe-west1)
ALLOY_DB_CLUSTER_NAME   # AlloyDB cluster (alloydb profile)
ALLOY_DB_INSTANCE_NAME  # AlloyDB instance (alloydb profile)
ALLOY_DB_USERNAME       # AlloyDB user (alloydb profile)
ALLOY_DB_PASSWORD       # AlloyDB password (alloydb profile)
```

## Domain Business Rules

Located in `TeamPlayerStats.java`:
- **Performance Rating**: Calculated from goals, assists, tackles, interceptions
- **Team Style**: OFFENSIVE / DEFENSIVE / BALANCED based on stat ratios
- **Goalkeeper Level**: ELITE (>=80%) / RELIABLE (>=70%) / AVERAGE
- **Star Players**: Players appearing in multiple "best" categories

## Code Conventions

- Java 23 with records for immutable models
- Functional style with Stream API
- Constructor injection for dependencies
- Profile-based adapter selection via Spring `@Profile`
- Non-root container execution (security)

## CI/CD

- **Standard Dockerfile**: `cicd/standard/Dockerfile` (eclipse-temurin:23-jre)
- **GraalVM Dockerfile**: `cicd/native-graalvm/Dockerfile` (distroless + native binary)
- **Cloud Build**: `cicd/*/deploy-cloud-run-service-*.yaml`
