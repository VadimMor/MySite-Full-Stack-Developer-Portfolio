package main.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;
}
