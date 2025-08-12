package blue_rabb.backend.controller;

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

    @GetMapping
    public ResponseEntity<MassiveProjectsResponse> getProjects(
            @RequestParam(value = "page", required = true) Integer page
    ) {
        MassiveProjectsResponse response = projectService.getProjects(page);
        return ResponseEntity.ok(response);
    }
}
