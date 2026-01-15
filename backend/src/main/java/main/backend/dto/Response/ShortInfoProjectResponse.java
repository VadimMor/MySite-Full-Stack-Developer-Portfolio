package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortInfoProjectResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;
}
