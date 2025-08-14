package blue_rabb.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExperienceResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;
}
