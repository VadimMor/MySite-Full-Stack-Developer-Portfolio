package blue_rabb.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty("date_create")
    Date dateCreate;

    @JsonProperty("technologies")
    TechnologyResponse[] technologyMassive;
}
