package blue_rabb.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import blue_rabb.backend.dto.response.FullInfoProjectResponse;
import blue_rabb.backend.dto.response.MassiveProjectsResponse;
import blue_rabb.backend.dto.response.ProjectCreateResponse;
import blue_rabb.backend.service.ProjectService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api-0.1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @Operation(
           summary = "Создать проект",
            description = "Создает проект и сохраняет в бд"
    )
    public ResponseEntity<ProjectCreateResponse> createProject(
            @Parameter(description = "Имя репозитория для поиска", required = true, example = "example")
            @RequestParam(value = "name", required = true) String name
    ) {
        ProjectCreateResponse response = projectService.createProject(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Получить массив кртакой информации о проектах",
            description = "Возвращает список проектов с постраничной навигацией"
    )
    public ResponseEntity<MassiveProjectsResponse> getProjects(
            @Parameter(description = "Страници для навигации", required = true, example = "0")
            @RequestParam(value = "page", required = true) Integer page
    ) {
        MassiveProjectsResponse response = projectService.getProjects(page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Получить информацию о проекте",
            description = "Возвращает полную информацию о проекте"
    )
    public ResponseEntity<FullInfoProjectResponse> getProject(
            @Parameter(description = "Имя репозитория", required = true, example = "example")
            @PathVariable(value = "name") String name
    ) {
        FullInfoProjectResponse response = projectService.getProject(name);
        return ResponseEntity.ok(response);
    }
}
