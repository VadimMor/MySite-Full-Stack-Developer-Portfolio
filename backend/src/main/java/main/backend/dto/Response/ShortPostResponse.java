package main.backend.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ShortPostResponse {
    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("date")
    Timestamp date;

    @JsonProperty("categories")
    FullCategoryResponse[] fullCategoryResponses;
}
