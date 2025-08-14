package blue_rabb.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FullInfoProjectResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty("date_create")
    Date dateCreate;

    @JsonProperty("date_update")
    Date dateUpdate;

    @JsonProperty("technologies")
    TechnologyResponse[] technologyMassive;

    @JsonProperty("posts")
    PostResponse[] MassivePostResponse;
}
