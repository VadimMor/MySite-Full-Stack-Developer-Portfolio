# --- СТАДИЯ 1: Сборка ---
FROM node:20-alpine AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы с зависимостями
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем остальной код приложения
COPY . .

# Собираем production-версию приложения
RUN npm run build

# --- СТАДИЯ 2: Запуск ---
FROM node:20-alpine AS runner

WORKDIR /app

# Копируем собранное приложение из стадии 'builder'
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/next.config.ts ./next.config.ts

# Устанавливаем только production-зависимости, используя новый синтаксис
RUN npm install --omit=dev

# Открываем порт, на котором будет работать приложение
EXPOSE 3000

# Команда для запуска приложения
CMD ["npm", "start"]
