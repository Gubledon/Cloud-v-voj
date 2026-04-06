# fee-service

Quarkus mikroslužba pre správu poplatkov (fees). Poskytuje REST API na vytváranie a získavanie poplatkov s podporou Docker a Kubernetes nasadenia vrátane GraalVM natívnej kompilácie.

## Technológie

- Quarkus 3.34.1
- Java 21
- Hibernate ORM Panache
- H2 databáza (in-memory pre dev/test, externá pre prod)
- RESTEasy + Jackson
- Swagger UI
- Basic autentifikácia (Elytron Security)
- Docker + Kubernetes + GraalVM (Mandrel)

## Spustenie v dev móde

```shell
./mvnw quarkus:dev
```

Dev UI je dostupné na http://localhost:8080/q/dev/

## Build a Docker

```shell
# Build produkčného artefaktu
./mvnw clean package -DskipTests

# Build s Docker image
./mvnw clean package -DskipTests -Dquarkus.container-image.build=true

# Spustenie externej H2 DB
docker run -d -p 9092:9092 -p 8082:8082 -v "$PWD/h2_data:/h2-data" --name=H2Instance thomseno/h2

# Spustenie fee-service kontajnera
docker run -p 8084:8080 \
  -e "minimal_fee_limit=10000" \
  -e "db_url=jdbc:h2:tcp://host.docker.internal:9092/test" \
  -e "db_username=sa" \
  -e "user_password=admin-password" \
  fmfi/fee-service
```

Aplikácia bude dostupná na http://localhost:8084

## Kubernetes nasadenie

```shell
# Vytvorenie ConfigMap a Secret
kubectl apply -f app-configmap.yml
kubectl apply -f app-secret.yml

# Build + deploy na K8s (JVM mód)
./mvnw package -DskipTests \
  -Dquarkus.container-image.build=true \
  -Dquarkus.kubernetes.deploy=true

# Build + deploy na K8s (natívny GraalVM mód)
./mvnw clean package -Pnative -DskipTests \
  -Dquarkus.native.container-build=true \
  -Dquarkus.container-image.build=true \
  -Dquarkus.kubernetes.deploy=true \
  -Dquarkus.native.builder-image=quay.io/quarkus/ubi9-quarkus-mandrel-builder-image:jdk-25

# Overenie stavu
kubectl get pods
kubectl get svc
```

### K8s konfigurácia

- **Service type:** LoadBalancer na porte 8084
- **Repliky:** 2
- **Health probes:** liveness (5s) + readiness (10s) cez SmallRye Health
- **ConfigMap (`app-configmap.yml`):** `db_url`, `db_username`, `minimal_fee_limit`
- **Secret (`app-secret.yml`):** `user_password` (base64)

### Prerekvizity pre K8s API

Pred deployom je potrebné nastaviť env premenné pre Kubernetes API TLS:

```shell
export CLIENT_CERT_DATA=<client-certificate-data z ~/.kube/config>
export CLIENT_CERT_KEY=<client-key-data z ~/.kube/config>
```

## REST API

| Metóda | Endpoint | Auth | Popis |
|--------|----------|------|-------|
| GET | `/fee` | user | Zoznam všetkých poplatkov |
| GET | `/fee?iban={iban}` | user | Poplatky pre daný IBAN |
| POST | `/fee` | admin | Vytvorenie nového poplatku |
| GET | `/hello` | - | Hello world |
| GET | `/q/health` | - | Health check |
| GET | `/q/swagger-ui` | - | Swagger UI |

## Natívna kompilácia

```shell
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Výsledný natívny executable: `./target/fee-service-1.0.0-SNAPSHOT-runner`
