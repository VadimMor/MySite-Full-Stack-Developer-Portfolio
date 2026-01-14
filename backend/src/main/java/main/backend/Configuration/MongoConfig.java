package main.backend.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    // Сюда попадет твоя строка из Docker Compose (MONGODB_URL)
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    // Сюда попадет имя базы данных (MONGODB_DB)
    @Value("${MONGODB_DB}")
    private String databaseName;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // Создаем шаблон для работы с базой, используя наш клиент и имя базы
        return new MongoTemplate(mongoClient(), databaseName);
    }
}
