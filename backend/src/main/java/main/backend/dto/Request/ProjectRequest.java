package main.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("short_description")
    @Size(max = 2000, message = "Short description cannot exceed 2000 characters.")
    private String shortDescription;

    @JsonProperty("description")
    @Size(max = 10000, message = "Description cannot exceed 10000 characters.")
    private String description;

    @JsonProperty("url")
    String url;

    @JsonProperty("technologies")
    List<TechnologyRequest> technologyRequestList;
}
