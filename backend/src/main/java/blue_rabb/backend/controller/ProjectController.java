package blue_rabb.backend.controller;

import blue_rabb.backend.dto.response.FullInfoProjectResponse;
import blue_rabb.backend.dto.response.MassiveProjectsResponse;
import blue_rabb.backend.dto.response.ProjectCreateResponse;
import blue_rabb.backend.service.ProjectService;
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
    public ResponseEntity<ProjectCreateResponse> createProject(
            @RequestParam(value = "name", required = true) String name
    ) {
        ProjectCreateResponse response = projectService.createProject(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Массив кртакой информации о проектах
     * @param page страница для вывода проектов
     * @return массив информации
     */
    @GetMapping
    public ResponseEntity<MassiveProjectsResponse> getProjects(
            @RequestParam(value = "page", required = true) Integer page
    ) {
        MassiveProjectsResponse response = projectService.getProjects(page);
        return ResponseEntity.ok(response);
    }

    /**
     * Метод вывода полной информации о проекте
     * @param name названгие проекта
     * @return полная инфомация
     */
    @GetMapping(name = "/{name}")
    public ResponseEntity<FullInfoProjectResponse> getProject(
            @PathVariable(value = "name") String name
    ) {
        FullInfoProjectResponse response = projectService.getProject(name);
        return ResponseEntity.ok(response);
    }
}
