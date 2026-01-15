package main.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddExperienceInSkillRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("experiences")
    List<String> experiences;
}
