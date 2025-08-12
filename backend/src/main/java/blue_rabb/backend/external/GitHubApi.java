package blue_rabb.backend.external;

import blue_rabb.backend.dto.response.GitHubRepositoryDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.net.http.HttpClient;


@Slf4j
@Component
public class GitHubApi {
    @Value("${github.url}")
    private String url;

    @Value("${github.token}")
    private String token;

    private WebClient webClient;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public GitHubRepositoryDTO getRepository(String name) {
        return webClient.get()
                    .uri("/repos/VadimMor/{repo}", name)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<GitHubRepositoryDTO>() {})
                    .block();
    }

    public Map<String, Integer> getLanguagesRepository(String name) {
        return webClient.get()
                .uri("/repos/VadimMor/{repo}/languages", name)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Integer>>() {})
                .block();
    }

    public String getReadme(String repoName) {
        try {
            String url = String.format(
                    "https://api.github.com/repos/%s/%s/readme",
                    "VadimMor",
                    repoName
            );
            System.out.println(url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/vnd.github.v3+json")
                    .header("Authorization", "Bearer " + token)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch README: " + response.statusCode());
            }

            // парсим JSON
            JsonNode json = objectMapper.readTree(response.body());
            String base64Content = json.get("content").asText();

            // Декодируем с учетом переносов строк
            byte[] decodedBytes = Base64.getMimeDecoder().decode(base64Content);
            return new String(decodedBytes, StandardCharsets.UTF_8);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error fetching README", e);
        }
    }
}
