package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Service.SkillService;
import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Request.SkillRequest;
import main.backend.dto.Response.ExperienceResponse;
import main.backend.dto.Response.FullSkillResponse;
import main.backend.dto.Response.SkillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-v0.2/skill")
@RequiredArgsConstructor
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping
    @Operation(
            summary = "Создание скилла",
            description = "Создает скилл и сохраняет в бд"
    )
    public ResponseEntity<SkillResponse> createSkill(
            @Parameter(description = "Информация о скилле", required = true)
            @RequestBody SkillRequest skillRequest
            ) {
        SkillResponse skillResponse = skillService.createSkill(skillRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillResponse);
    }

    @PostMapping("/experience")
    @Operation(
            summary = "Создание опыта",
            description = "Создает опыта и сохраняет в бд"
    )
    public ResponseEntity<ExperienceResponse> createExperience(
            @Parameter(description = "Информация о опыте", required = true)
            @RequestBody ExperienceRequest experienceRequest
            ) {
        ExperienceResponse experienceResponse = skillService.createExperience(experienceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceResponse);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Вывод всех скиллов с опытом",
            description = "Выводит все скиллы с опытом"
    )
    public ResponseEntity<FullSkillResponse[]> getMassiveSkill() {
        FullSkillResponse[] fullSkillResponses = skillService.getMassiveSkill();
        return ResponseEntity.ok(fullSkillResponses);
    }
}
