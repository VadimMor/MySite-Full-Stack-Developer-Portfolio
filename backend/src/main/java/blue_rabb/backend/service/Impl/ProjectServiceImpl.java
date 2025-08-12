package blue_rabb.backend.service.Impl;

import blue_rabb.backend.dto.response.*;
import blue_rabb.backend.entity.Project;
import blue_rabb.backend.entity.Technology;
import blue_rabb.backend.external.GitHubApi;
import blue_rabb.backend.repository.ProjectRepository;
import blue_rabb.backend.service.ProjectService;
import blue_rabb.backend.service.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    @Value("${github.token}")
    private String token;

    @Autowired
    private GitHubApi gitHubApi;

    @Autowired
    private TechnologyService technologyService;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Метод создания проекта и сохранения в бд
     * @param name название репозитория
     * @return статус сохранения
     */
    @Override
    public ProjectCreateResponse createProject(String name) {
        log.info("Start create project - {}", name);
        Project project = projectRepository.getByName(name);

        if (project != null) {
            throw new RuntimeException();
        }

        GitHubRepositoryDTO repositoryData = gitHubApi.getRepository(name);

        if (repositoryData == null) {
            throw new RuntimeException();
        }

        Set<Technology> technologySet = new HashSet<>();
        Map<String, Integer> languages = gitHubApi.getLanguagesRepository(name);
        languages.forEach((language, bytes) -> {
            technologySet.add(
                    technologyService.createTechnologyByGitHub(language)
            );
        });

        project = new Project(
                repositoryData.getName(),
                repositoryData.getDescription(),
                repositoryData.getCreatedAt(),
                technologySet
        );

        projectRepository.saveAndFlush(project);

        return new ProjectCreateResponse(
                project.getName(),
                true
        );
    }

    @Override
    public MassiveProjectsResponse getProjects(Integer page) {
        List<Project> projects = projectRepository.getListByPage(page * 20);

        boolean lastPage = ((page + 1) * 20L) >= projectRepository.getTotalProjectsCount();

        ProjectResponse[] massiveProjectsResponse = projects.stream()
                .map(project -> new ProjectResponse(
                    project.getName(),
                    project.getDescription(),
                    project.getDateCreate(),
                    project.getTechnologies().stream()
                            .map(technology -> new TechnologyResponse(technology.getName()))
                            .toArray(TechnologyResponse[]::new)
                ))
                .toArray(ProjectResponse[]::new);

        return new MassiveProjectsResponse(
                massiveProjectsResponse,
                lastPage
        );
    }
}
