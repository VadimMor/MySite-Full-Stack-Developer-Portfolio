FROM gradle:9.0-jdk24 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test --no-daemon

FROM openjdk:24-ea-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
