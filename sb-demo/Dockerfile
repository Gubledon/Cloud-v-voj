FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder

WORKDIR /build

COPY . .

RUN mvn clean install -Dmaven.test.skip=true -Dmaven.gitcommitid.skip=true

FROM eclipse-temurin:21-jdk

COPY --from=builder /build/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
