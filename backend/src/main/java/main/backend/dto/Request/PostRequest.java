package main.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    @Size(max = 10000, message = "Description cannot exceed 10000 characters.")
    String description;

    @JsonProperty("url")
    String url;

    @JsonProperty("categories")
    List<CategoryRequest> categories;
}
