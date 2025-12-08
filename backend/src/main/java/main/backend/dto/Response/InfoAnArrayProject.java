package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class InfoAnArrayProject {
    @JsonProperty("date")
    Timestamp date;

    @JsonProperty("name")
    String name;

    @JsonProperty("technologies")
    TechnologyResponse[] technologyResponses;
}
