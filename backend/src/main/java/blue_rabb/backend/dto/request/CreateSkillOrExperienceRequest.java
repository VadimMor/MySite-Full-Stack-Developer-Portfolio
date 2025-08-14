package blue_rabb.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSkillOrExperienceRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;
}
