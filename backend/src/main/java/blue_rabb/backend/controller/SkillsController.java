package blue_rabb.backend.controller;

import blue_rabb.backend.dto.request.CreateSkillOrExperienceRequest;
import blue_rabb.backend.dto.response.SkillResponse;
import blue_rabb.backend.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-0.1/skills")
@RequiredArgsConstructor
public class SkillsController {
    private final SkillService skillService;

    @GetMapping
    @Operation(
            summary = "Вывести список скиллов",
            description = "Выводит скиллы и информацию о них"
    )
    public ResponseEntity<SkillResponse[]> getSkills() {
        SkillResponse[] massiveSkillResponse = skillService.getMassiveSkill();
        return ResponseEntity.ok(massiveSkillResponse);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Сохранить информацию о скилле",
            description = "Создает информацию о скилле и сохраняет в бд"
    )
    public ResponseEntity<String> createSkill(
            @Parameter(description = "Данные о скилле", required = true)
            @RequestBody CreateSkillOrExperienceRequest createSkillOrExperienceRequest
    ) {
        skillService.createSkill(createSkillOrExperienceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Information skill save");
    }

    @PostMapping("/experience/create")
    @Operation(
            summary = "Сохранить информацию о опыте",
            description = "Создает информацию о опыте скилла и сохраняет в бд"
    )
    public ResponseEntity<String> createExperience(
            @Parameter(description = "Данные о опыте", required = true)
            @RequestBody CreateSkillOrExperienceRequest createSkillOrExperienceRequest,

            @Parameter(description = "Данные о опыте", required = true)
            @RequestParam(name = "name_skill") String nameSkill
    ) {
        skillService.createExperience(createSkillOrExperienceRequest, nameSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body("Information experience save");
    }
}
