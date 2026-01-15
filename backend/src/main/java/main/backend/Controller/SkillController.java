package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Service.SkillService;
import main.backend.dto.Request.*;
import main.backend.dto.Response.ExperienceResponse;
import main.backend.dto.Response.FullExperienceResponse;
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

    @GetMapping("/all")
    @Operation(
            summary = "Вывод всех скиллов с опытом",
            description = "Выводит все скиллы с опытом"
    )
    public ResponseEntity<FullSkillResponse[]> getMassiveSkill() {
        FullSkillResponse[] fullSkillResponses = skillService.getMassiveSkill();
        return ResponseEntity.ok(fullSkillResponses);
    }

    @PutMapping("/status")
    @Operation(
            summary = "Обновление статуса скилла",
            description = "обновляет статус скилла и сохраняет в бд"
    )
    public ResponseEntity<SkillResponse> updateStatusSkill(
            @Parameter(description = "Информация скилла для обновления статуса", required = true)
            @RequestBody UpdateStatusSkill updateStatusSkill
    ) {
        SkillResponse skillResponse = skillService.updateStatusSkill(updateStatusSkill);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(skillResponse);
    }

    @PutMapping("/add")
    @Operation(
            summary = "Добавление опыта к скиллу",
            description = "Добавляет массим опыта к скиллу и сохраняет в бд"
    )
    public ResponseEntity<SkillResponse> addExperienceInSkill(
            @Parameter(description = "Массив опыта для добавления", required = true)
            @RequestBody AddExperienceInSkillRequest addExperienceInSkill
    ) {
        SkillResponse skillResponse = skillService.addExperienceInSkill(addExperienceInSkill);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(skillResponse);
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

    @PutMapping("/experience/status")
    @Operation(
            summary = "Обновление статуса опыта",
            description = "обновляет статус опыта и сохраняет в бд"
    )
    public ResponseEntity<ExperienceResponse> updateStatusExperience(
            @Parameter(description = "Информация опыта для обновления статуса", required = true)
            @RequestBody UpdateStatusExperience updateStatusExperience
    ) {
        ExperienceResponse experienceResponse = skillService.updateStatusExperience(updateStatusExperience);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(experienceResponse);
    }

    @GetMapping("/experience/all")
    @Operation(
            summary = "Вывод всего опыта",
            description = "Выводит всех возможный открытый опыт"
    )
    public ResponseEntity<FullExperienceResponse[]> getAllExperience() {
        FullExperienceResponse[] fullExperienceResponses = skillService.getMassiveExperience();
        return ResponseEntity.ok(fullExperienceResponses);
    }
}
