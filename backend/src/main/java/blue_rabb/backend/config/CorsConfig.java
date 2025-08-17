package blue_rabb.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применяет CORS ко всем путям
                .allowedOrigins("*") // Укажите разрешенные домены
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешенные методы
                .allowedHeaders("*") // Разрешенные заголовки
                .allowCredentials(false); // Разрешение отправки куки и заголовков авторизации
    }
}