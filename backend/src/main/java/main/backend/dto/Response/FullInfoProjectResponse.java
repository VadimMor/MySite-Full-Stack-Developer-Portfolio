package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class FullInfoProjectResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    @Size(max = 10000, message = "Description cannot exceed 10000 characters.")
    String description;

    @JsonProperty("create_date")
    Timestamp createDate;

    @JsonProperty("url")
    String url;

    @JsonProperty("technologies")
    TechnologyResponse[] technologyResponses;
}
