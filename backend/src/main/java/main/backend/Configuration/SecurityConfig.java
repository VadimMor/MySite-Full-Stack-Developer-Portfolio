package main.backend.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${admin.spring.username:admin}")
    private String adminUsername;

    @Value("${admin.spring.password:password}")
    private String adminPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername(adminUsername)
                .password(encoder.encode(adminPassword))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // --- ПУБЛИЧНЫЕ РЕСУРСЫ ---
                        // Просмотр контента (все GET-запросы к API)
                        .requestMatchers(HttpMethod.GET, "/api-v0.2/**").permitAll()
                        // Подписка на рассылку
                        .requestMatchers("/api-v0.2/email/**").permitAll()
                        // Просмотр ошибок
                        .requestMatchers("/error").permitAll()
                        // --- ТОЛЬКО ДЛЯ АДМИНА ---
                        // Swagger UI и JSON-документация
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").hasRole("ADMIN")
                        // Навыки (Skills) - POST/PUT методы
                        .requestMatchers(HttpMethod.PUT, "/api-v0.2/skill/status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-v0.2/skill/experience/status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-v0.2/skill/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-v0.2/skill/**").hasRole("ADMIN")
                        // Проекты (Projects) - POST/PUT методы
                        .requestMatchers(HttpMethod.PUT, "/api-v0.2/project/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-v0.2/project/**").hasRole("ADMIN")
                        // Посты и категории (Posts) - POST/PUT методы
                        .requestMatchers(HttpMethod.PUT, "/api-v0.2/post/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-v0.2/post/**").hasRole("ADMIN")
                        // Файлы (Files) - POST/DELETE методы
                        .requestMatchers(HttpMethod.POST, "/api-v0.2/file/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-v0.2/file/**").hasRole("ADMIN")
                        // Защита всех остальных неописанных эндпоинтов
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Разрешенные домены
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080",
                "http://blue-rabbit.ru:8080",
                "http://localhost:3000",
                "http://blue-rabbit.ru:3000"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
