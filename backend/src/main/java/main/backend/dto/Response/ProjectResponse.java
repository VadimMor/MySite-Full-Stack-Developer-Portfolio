package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("status")
    Boolean status;
}
