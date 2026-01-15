package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import main.backend.enums.StatusVisibility;

@Data
@AllArgsConstructor
public class SkillResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("status")
    StatusVisibility status;
}
