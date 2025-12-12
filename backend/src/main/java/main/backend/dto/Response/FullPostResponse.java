package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class FullPostResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty(value = "url", required = false)
    String url;

    @JsonProperty("length")
    Integer length;

    @JsonProperty("date")
    Timestamp date;

    @JsonProperty("categories")
    FullCategoryResponse[] fullCategoryRespons;
}
