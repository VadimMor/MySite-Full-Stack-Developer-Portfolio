# --- СТАДИЯ 1: Сборка ---
# Используем образ с JDK для сборки проекта. Версия Java 21.
FROM eclipse-temurin:24-jdk-alpine AS builder
# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем Gradle Wrapper
COPY gradlew .
COPY gradle ./gradle

# Даем файлу gradlew права на исполнение
RUN chmod +x ./gradlew

# Копируем файлы с зависимостями для кэширования слоев
COPY build.gradle settings.gradle ./

# Загружаем зависимости
# Это ускорит последующие сборки, если зависимости не менялись
RUN ./gradlew dependencies --no-daemon || true

# Копируем исходный код
COPY src ./src

# Собираем приложение в исполняемый JAR-файл
# --no-daemon используется для лучшей работы в CI/CD и Docker
RUN ./gradlew bootJar --no-daemon


# --- СТАДИЯ 2: Запуск ---
# Используем более легковесный образ только с JRE.
FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app

# Копируем собранный JAR-файл из стадии 'builder'
COPY --from=builder /app/build/libs/*.jar app.jar

# Открываем порт, который слушает приложение Spring Boot (по умолчанию 8080)
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]