package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("status")
    String status;
}
