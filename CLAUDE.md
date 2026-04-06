# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Anonymity Policy

This is a school project. Do not leave any traces of AI assistance:
- Never include `Co-Authored-By` lines in git commits
- Do not add comments referencing Claude, AI, or code generation
- Do not mention Claude Code in PR descriptions, commit messages, or any checked-in files
- Write code in a natural, student-like style

## Git

Always use SSH for git operations. The remote URL should be `git@github.com:Gubledon/Cloud-v-voj.git`.

## Project Overview

This repository contains two projects:

1. **sb-demo** — Spring Boot 4.0.2 demo web application (Maven, Java 25) under the `fmfi.sbdemo` package. Uses Spring MVC, Spring Data JPA with H2 (embedded) + PostgreSQL, Actuator, Lombok, and DevTools.
2. **fee-service** — Quarkus 3.34.1 microservice (Maven, Java 21) under the `sk.fmfi` package. Provides a Fee REST API with H2, Hibernate ORM Panache, Swagger UI, basic auth, and full Docker/Kubernetes/GraalVM native support.

## sb-demo Build & Development Commands

All commands run from the `sb-demo/` directory using the Maven wrapper:

```bash
./mvnw spring-boot:run          # Run the application (default H2 profile)
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres   # Run with PostgreSQL (postgres1)
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres2  # Run with PostgreSQL (postgres2)
./mvnw clean package            # Build JAR (output in target/)
./mvnw clean package -DskipTests # Build JAR without tests
./mvnw test                     # Run all tests
./mvnw test -Dtest=ClassName    # Run a single test class
./mvnw test -Dtest=ClassName#methodName  # Run a single test method
```

On Windows use `mvnw.cmd` instead of `./mvnw`.

## Docker Commands

Run from `sb-demo/` directory:

```bash
docker-compose up --build       # Start all services (build images first)
docker-compose up -d            # Start in background
docker-compose down             # Stop all services
docker-compose down -v          # Stop and remove volumes
docker-compose logs java        # View java app logs
docker-compose logs postgres1   # View postgres1 logs
```

## Project Structure

```
sb-demo/
├── pom.xml                          # Maven config (Spring Boot 4.0.2, Java 25)
├── mvnw / mvnw.cmd                  # Maven wrapper
├── Dockerfile                       # Java app container (eclipse-temurin:21-jdk)
├── docker-compose.yml               # All services: java, postgres1, postgres2, java-build
├── init.sql                         # DB init script for postgres2 (cvicenie_docker schema)
├── docker/
│   └── postgresql/
│       ├── Postgres.Dockerfile      # Custom PostgreSQL image with init.sql
│       └── init.sql                 # Same init script copied into image
├── k8s/
│   ├── namespace.yaml               # Namespace sb-demo
│   ├── configmap.yaml               # App config + PostgreSQL init.sql
│   ├── secret.yaml                  # DB credentials (base64)
│   ├── pvc.yaml                     # Persistent storage for PostgreSQL
│   ├── postgres-deployment.yaml     # PostgreSQL deployment
│   ├── postgres-service.yaml        # PostgreSQL ClusterIP service
│   ├── java-deployment.yaml         # Java app deployment
│   └── java-service.yaml            # Java app LoadBalancer service
├── src/
│   ├── main/
│   │   ├── java/fmfi/sbdemo/
│   │   │   ├── SbDemoApplication.java
│   │   │   ├── HelloWorldController.java
│   │   │   ├── HelloConfigProperties.java
│   │   │   ├── adapter/
│   │   │   │   ├── integration/
│   │   │   │   │   ├── IntegrationAdapterConfiguration.java
│   │   │   │   │   ├── FeesBackendRestClientAdapter.java
│   │   │   │   │   ├── PaymentOrderBackendSystemMockAdapter.java
│   │   │   │   │   └── IntegrationMockServer.java
│   │   │   │   ├── persistence/
│   │   │   │   │   ├── TransactionEntity.java
│   │   │   │   │   ├── TransactionRepository.java
│   │   │   │   │   ├── TransactionPersistenceAdapter.java
│   │   │   │   │   ├── AccountEmbeddable.java
│   │   │   │   │   ├── MoneyEmbeddable.java
│   │   │   │   │   ├── PaymentDetailEmbeddable.java
│   │   │   │   │   └── PaymentSymbolsEmbeddable.java
│   │   │   │   └── rest/
│   │   │   │       ├── PaymentOrderRestController.java
│   │   │   │       └── TransactionRestController.java
│   │   │   └── core/
│   │   │       ├── api/
│   │   │       │   ├── CreatePaymentOrderUseCase.java
│   │   │       │   ├── GetLatestCurrentAccountTransactionsUseCase.java
│   │   │       │   ├── dictionary/ (Money, TransactionStatus, TransactionDirection)
│   │   │       │   └── dto/ (PaymentOrderDto, PaymentOrderRequestDto, etc.)
│   │   │       └── domain/
│   │   │           ├── PaymentOrderService.java
│   │   │           ├── CurrentAccountTransactionService.java
│   │   │           └── SPI interfaces
│   │   └── resources/
│   │       ├── application.yaml              # Default config (H2)
│   │       ├── application-postgres.yml      # Profile: postgres1 (localhost:5432/cloud)
│   │       ├── application-postgres2.yml     # Profile: postgres2 (localhost:5433/cvicenie_docker)
│   │       └── data.sql                      # Sample data for H2
│   └── test/
│       ├── java/fmfi/sbdemo/
│       │   └── ...tests...
│       └── resources/
│           └── application.yaml              # Test config
└── target/                                   # Build output
```

## Architecture

Standard Spring Boot layered architecture (hexagonal/ports-and-adapters):

- **Entry point:** `src/main/java/fmfi/sbdemo/SbDemoApplication.java`
- **Config:** `src/main/resources/application.yaml`
- **Tests:** `src/test/java/fmfi/sbdemo/`

Lombok is configured as an annotation processor in the Maven compiler plugin — use `@Data`, `@Builder`, etc. freely. Lombok is excluded from the final JAR.

## Database Profiles

| Profile | DB | Host:Port | Database | User | Password |
|---------|-----|-----------|----------|------|----------|
| (default) | H2 | embedded | fmfi | sa | (empty) |
| postgres | PostgreSQL | localhost:5432 | cloud | user | password |
| postgres2 | PostgreSQL | localhost:5433 | cvicenie_docker | cvicenie_docker | cvicenie_docker |

## Docker Services (docker-compose.yml)

| Service | Description | Ext Port | Image/Build |
|---------|-------------|----------|-------------|
| postgres1 | PostgreSQL with persistent volume | 5432 | postgres:latest |
| postgres2 | PostgreSQL with init.sql (custom Dockerfile) | 5433 | docker/postgresql/Dockerfile |
| java | Spring Boot app container | 8080 | Dockerfile (eclipse-temurin:21-jdk) |
| java-build | Multistage build (bonus) | - | maven:3.9.6-eclipse-temurin-21-jammy |

All services share the `cloud-network` bridge network.

## Docker Build Notes

The Dockerfile uses a multistage build:
1. **Builder stage** (`maven:3.9.6-eclipse-temurin-21-jammy`): compiles the project with Maven
2. **Runtime stage** (`eclipse-temurin:21-jdk`): runs the JAR

The `git-commit-id-maven-plugin` in `pom.xml` requires a `.git` directory. Since the Docker build context is `sb-demo/` (and `.git` lives one level up), the Dockerfile passes `-Dmaven.gitcommitid.skip=true` to skip this plugin during Docker builds.

## Kubernetes Deployment

### Prerequisites
- Docker Desktop with Kubernetes enabled
- `kubectl` installed (bundled with Docker Desktop)
- Optional: `k9s` for terminal-based K8s UI (https://k9scli.io)

### K8s Manifest Files (`sb-demo/k8s/`)

| File | Object | Purpose |
|------|--------|---------|
| `namespace.yaml` | Namespace `sb-demo` | Isolates all K8s objects in a dedicated namespace |
| `configmap.yaml` | ConfigMap `app-config` | Spring config: profile, datasource URL, JPA settings |
| `configmap.yaml` | ConfigMap `postgres-init` | Contains `init.sql` script mounted into PostgreSQL |
| `secret.yaml` | Secret `postgres-secret` | DB credentials (base64): user/password |
| `pvc.yaml` | PersistentVolumeClaim `postgres-pvc` | 1Gi persistent storage for PostgreSQL data |
| `postgres-deployment.yaml` | Deployment `postgres` | Runs postgres:16, mounts PVC + init.sql from ConfigMap |
| `postgres-service.yaml` | Service `postgres` (ClusterIP) | Internal-only DB access on port 5432 |
| `java-deployment.yaml` | Deployment `java-app` | Runs sb-demo:latest with env from ConfigMap + Secret |
| `java-service.yaml` | Service `java-app` (LoadBalancer) | Exposes app externally on port 8080 |

### K8s Architecture

- **Java app Service** uses `LoadBalancer` type — accessible from the browser at `http://localhost:8080`
- **PostgreSQL Service** uses `ClusterIP` type — only accessible within the cluster (by the Java app)
- The Java app connects to PostgreSQL using the K8s service name `postgres` as hostname
- `imagePullPolicy: Never` is set on the Java app deployment so K8s uses the locally built Docker image

### Kubernetes Commands

```bash
# Build the Docker image (required before first K8s deploy)
cd sb-demo/
docker build -t sb-demo:latest .

# Deploy all manifests
kubectl apply -f k8s/

# Check status
kubectl get all -n sb-demo
kubectl get pods -n sb-demo

# View logs
kubectl logs -f deployment/java-app -n sb-demo
kubectl logs -f deployment/postgres -n sb-demo

# Stop without deleting (scale to 0)
kubectl scale deployment java-app postgres --replicas=0 -n sb-demo

# Start again (scale to 1)
kubectl scale deployment java-app postgres --replicas=1 -n sb-demo

# Delete everything
kubectl delete namespace sb-demo

# Monitor with k9s
k9s -n sb-demo
```

### How K8s Runs on Docker Desktop

Kubernetes uses Docker as its container runtime. Each K8s pod appears as separate containers in Docker Desktop (not grouped like docker-compose). Each pod creates:
1. A **pause container** (`registry.k8s.io/pause`) — holds the network namespace
2. The **actual application container** (e.g., `sb-demo:latest` or `postgres:16`)

Use `kubectl` or `k9s` for the proper Kubernetes-native view of pods and services.

## fee-service

### Build & Development Commands

All commands run from the `fee-service/` directory using the Maven wrapper:

```bash
./mvnw quarkus:dev                # Run in dev mode (live coding, H2 in-memory)
./mvnw clean package -DskipTests  # Build production JAR
./mvnw test                       # Run all tests
```

On Windows (PowerShell) use `.\mvnw.cmd` and quote `-D` flags: `"-Dparam=value"`.

### Docker Commands

```bash
# Build Docker image (JVM mode)
./mvnw clean package -DskipTests -Dquarkus.container-image.build=true

# Run fee-service container
docker run -p 8084:8080 -e "minimal_fee_limit=10000" -e "db_url=jdbc:h2:tcp://host.docker.internal:9092/test" -e "db_username=sa" -e "user_password=admin-password" fmfi/fee-service

# Start external H2 DB
docker run -d -p 9092:9092 -p 8082:8082 -v "$PWD/h2_data:/h2-data" --name=H2Instance thomseno/h2
```

### Docker Image Config (in application.properties)

| Property | Value |
|----------|-------|
| `quarkus.container-image.group` | fmfi |
| `quarkus.container-image.name` | fee-service |
| `quarkus.container-image.tag` | latest |

### fee-service Profiles

| Profile | DB | Datasource URL |
|---------|-----|----------------|
| dev/test | H2 (in-memory) | `jdbc:h2:mem:test` |
| prod | H2 (external) | `${db_url}` (from ConfigMap) |

### Kubernetes Deployment

fee-service uses Quarkus-native Kubernetes support. Manifests are auto-generated in `target/kubernetes/`.

```bash
# Apply ConfigMap and Secret
kubectl apply -f app-configmap.yml
kubectl apply -f app-secret.yml

# Build + deploy to K8s (JVM mode)
./mvnw package -DskipTests -Dquarkus.container-image.build=true -Dquarkus.kubernetes.deploy=true

# Build + deploy to K8s (native GraalVM mode)
./mvnw clean package -Pnative -DskipTests -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.kubernetes.deploy=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi9-quarkus-mandrel-builder-image:jdk-25

# Check status
kubectl get pods
kubectl get svc
```

### K8s Manifest Files (`fee-service/`)

| File | Object | Purpose |
|------|--------|---------|
| `app-configmap.yml` | ConfigMap `app-configmap` | `db_url`, `db_username`, `minimal_fee_limit` |
| `app-secret.yml` | Secret `app-secret` | `user_password` (base64) |

### K8s Configuration (in application.properties)

- **Namespace:** default
- **Service type:** LoadBalancer on port 8084
- **Image pull policy:** never (uses local Docker image)
- **Replicas:** 2
- **Health probes:** liveness (5s period) + readiness (10s period) via SmallRye Health
- **K8s API:** connects via `https://kubernetes.docker.internal:6443` with TLS client certs (`CLIENT_CERT_DATA`, `CLIENT_CERT_KEY` env vars)

### fee-service Project Structure

```
fee-service/
├── pom.xml                          # Maven config (Quarkus 3.34.1, Java 21)
├── mvnw / mvnw.cmd                  # Maven wrapper
├── app-configmap.yml                # K8s ConfigMap manifest
├── app-secret.yml                   # K8s Secret manifest
├── h2_data/                         # External H2 persistent storage
├── src/
│   ├── main/
│   │   ├── docker/
│   │   │   ├── Dockerfile.jvm       # JVM mode Docker image
│   │   │   ├── Dockerfile.native    # Native mode Docker image
│   │   │   ├── Dockerfile.legacy-jar
│   │   │   └── Dockerfile.native-micro
│   │   ├── java/sk/fmfi/
│   │   │   ├── GreetingResource.java
│   │   │   ├── AdvancedGreetingResource.java
│   │   │   ├── MyEntity.java
│   │   │   ├── model/
│   │   │   │   └── Fee.java
│   │   │   ├── repository/
│   │   │   │   └── FeeRepository.java
│   │   │   ├── resource/
│   │   │   │   ├── FeeResource.java
│   │   │   │   └── dto/TransactionDTO.java
│   │   │   └── service/
│   │   │       ├── FeeService.java
│   │   │       └── FeeServiceBean.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── import.sql
│   └── test/
│       └── java/sk/fmfi/
│           ├── FeeResourceTest.java
│           ├── GreetingResourceTest.java
│           ├── GreetingResourceIT.java
│           └── AdvancedGreetingResourceTest.java
└── target/
    └── kubernetes/                  # Auto-generated K8s manifests
```
