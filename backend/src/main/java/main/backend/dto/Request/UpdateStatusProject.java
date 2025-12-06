package main.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import main.backend.enums.StatusVisibility;

@Data
@AllArgsConstructor
public class UpdateStatusProject {
    @JsonProperty("name")
    String name;

    @JsonProperty("status")
    String status;
}
