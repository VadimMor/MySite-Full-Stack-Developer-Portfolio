package main.backend.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStatusSkill {
    @JsonProperty("name")
    String name;

    @JsonProperty("status")
    String status;
}
