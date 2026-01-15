# Этап сборки (Build stage)
FROM gradle:8.7-jdk21-alpine AS builder
# Примечание: Для Java 24, возможно, придется использовать 'gradle:jdk24'
# если он доступен, или установить JDK вручную, так как это очень новая версия.
# Пока используем совместимый образ или более общий.
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test --no-daemon

# Этап запуска (Run stage)
# Используем OpenJDK 24 (Early Access), так как указано в build.gradle
FROM openjdk:24-ea-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]