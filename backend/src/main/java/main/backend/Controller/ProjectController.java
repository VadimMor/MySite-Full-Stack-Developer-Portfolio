package main.backend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Service.ProjectService;
import main.backend.dto.Request.ProjectRequest;
import main.backend.dto.Request.UpdateStatusProject;
import main.backend.dto.Response.FullInfoProjectResponse;
import main.backend.dto.Response.ProjectResponse;
import main.backend.dto.Response.ShortInfoProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-v0.2/project")
@RequiredArgsConstructor
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    @Operation(
            summary = "Создание проекта",
            description = "Создает проект и сохраняет в бд"
    )
    public ResponseEntity<ProjectResponse> createProject(
            @Parameter(description = "Информация о проекте", required = true)
            @RequestBody ProjectRequest projectRequest
            ) {
        ProjectResponse projectResponse = projectService.createProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }

    @PutMapping
    @Operation(
            summary = "Обновление статуса проекта",
            description = "Обновляет статус проекта и сохраняет в бд"
    )
    public ResponseEntity<ProjectResponse> updateStatusProject(
            @Parameter(description = "Информация для обновления статуса проекта", required = true)
            @RequestBody UpdateStatusProject updateStatusProject
            ) {
        ProjectResponse projectResponse = projectService.updateStatus(updateStatusProject);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(projectResponse);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Вывод информации о проекте",
            description = "Вывод полной информации о проекте для пользователя"
    )
    public ResponseEntity<FullInfoProjectResponse> getProject(
            @Parameter(description = "Название проекта", required = true, example = "example")
            @PathVariable("name") String name
    ) {
        FullInfoProjectResponse fullInfoProjectResponse = projectService.getProject(name);
        return ResponseEntity.ok(fullInfoProjectResponse);
    }

    @GetMapping("/short/{name}")
    @Operation(
            summary = "Вывод краткой информации о проекте",
            description = "Вывод ознокомительной информации о проекте для пользователя"
    )
    public ResponseEntity<ShortInfoProjectResponse> getShortProject(
            @Parameter(description = "Название проекта", required = true, example = "example")
            @PathVariable("name") String name
    ) {
        ShortInfoProjectResponse shortInfoProjectResponse = projectService.getShortProject(name);
        return ResponseEntity.ok(shortInfoProjectResponse);
    }
}
