package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Project;
import main.backend.Repository.ProjectRepository;
import main.backend.Service.ProjectService;
import main.backend.Service.TechnologyService;
import main.backend.dto.Request.ProjectRequest;
import main.backend.dto.Response.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TechnologyService technologyService;

    /**
     * Создание и сохранение нового проекта
     * @param projectRequest информация о проекте
     * @return название и статус созданного проекта
     */
    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        log.trace("Search project by name - {}", projectRequest.getName());
        Project project = projectRepository.getByNameAndStatus(projectRequest.getName(), true);

        if (project != null) {
            log.error("The project has already been created - {}", projectRequest.getName());
            throw new RuntimeException("The project has already been created");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Create project by name - {}", projectRequest.getName());
        Project newProject = new Project(
                now,
                now,
                false,
                projectRequest.getName(),
                projectRequest.getShortDescription(),
                projectRequest.getDescription(),
                projectRequest.getUrl()
        );

        projectRequest.getTechnologyRequestList().forEach(technologyRequest -> {
            log.trace("Add technology in project - {}", technologyRequest.getName());
            newProject.addTechnology(
                    technologyService.createTechnology(technologyRequest.getName())
            );
        });

        log.trace("Save project by name - {}", newProject.getName());
        projectRepository.saveAndFlush(newProject);

        return new ProjectResponse(
                newProject.getName(),
                false
        );
    }
}
