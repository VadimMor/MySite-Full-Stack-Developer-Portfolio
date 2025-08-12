package blue_rabb.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MassiveProjectsResponse {
    @JsonProperty("projects")
    ProjectResponse[] massiveProjectsResponse;

    @JsonProperty("last_page")
    boolean lastPage;
}
