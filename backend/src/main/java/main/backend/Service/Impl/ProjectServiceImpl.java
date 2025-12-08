package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Project;
import main.backend.Repository.ProjectRepository;
import main.backend.Service.ProjectService;
import main.backend.Service.TechnologyService;
import main.backend.dto.Request.ProjectRequest;
import main.backend.dto.Request.UpdateStatusProject;
import main.backend.dto.Response.*;
import main.backend.enums.SortVisibility;
import main.backend.enums.StatusVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
        Project project = projectRepository.getByName(projectRequest.getName());

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
                StatusVisibility.DEVELOPMENT,
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
                newProject.getStatus()
        );
    }

    /**
     * Обновление статуса проекта
     * @param updateStatusProject информация для обновления
     * @return обновленный статус проекта
     */
    @Override
    public ProjectResponse updateStatus(UpdateStatusProject updateStatusProject) {
        log.trace("Search project by name - {}", updateStatusProject.getName());
        Project project = projectRepository.getByName(updateStatusProject.getName());

        if (project == null) {
            log.error("The project not found - {}", updateStatusProject.getName());
            throw new RuntimeException("The project not found");
        }

        log.trace("Update status \"{}\" project - {}", updateStatusProject.getStatus(), updateStatusProject.getName());
        project.setStatus(
                StatusVisibility.fromStatus(updateStatusProject.getStatus())
        );

        log.trace("Save project by name - {}", project.getName());
        projectRepository.save(project);

        return new ProjectResponse(
                project.getName(),
                project.getStatus()
        );
    }

    /**
     * Вывод полной информации о проекте
     * @param name название проекта
     * @return информация о проекте
     */
    @Override
    public FullInfoProjectResponse getProject(String name) {
        log.trace("Search project by name - {}", name);
        Project project = projectRepository.getByNameAndStatus(name,StatusVisibility.OPEN);

        if (project == null) {
            log.error("The project not found - {}", name);
            throw new RuntimeException("The project not found");
        }

        log.trace("Get info project by name - {}", name);
        return new FullInfoProjectResponse(
                project.getName(),
                project.getDescription(),
                project.getCreateDate(),
                project.getUrl(),
                project
                        .getTechnologies()
                        .stream()
                        .map(
                                technology -> new TechnologyResponse(
                                        technology.getName()
                                )
                        ).toArray(
                                TechnologyResponse[]::new
                        )
        );
    }

    /**
     * Вывод краткой информации о проекте
     * @param name название проекта
     * @return информация о проекте
     */
    @Override
    public ShortInfoProjectResponse getShortProject(String name) {
        log.trace("Search project by name - {}", name);
        Project project = projectRepository.getByNameAndStatus(name,StatusVisibility.OPEN);

        if (project == null) {
            log.error("The project not found - {}", name);
            throw new RuntimeException("The project not found");
        }

        return new ShortInfoProjectResponse(
                project.getName(),
                project.getShortDescription()
        );
    }

    /**
     * Вывод массива проектов по сортировке
     * @param sort сортировка по признаку
     * @param page страница для вывода проектов
     * @return массив проектов
     */
    @Override
    public InfoAnArrayProject[] getMassiveProjects(String sort, Integer page) {
        log.trace("Getting projects with sort: '{}' and page: {}", sort, page);
        List<Project> projectList = null;

        if (sort != null && page == null) {
            String[] sortMassive = sort.split("_");
            log.trace("Sorting requested without page: field='{}', direction='{}', using page 0", sortMassive[0], sortMassive[1]);

            if (Objects.equals(sortMassive[0], "date")) {
                projectList = findProjectsBySortAndPage(
                        "createDate",
                        sortMassive[1],
                        0
                );
            } else if (Objects.equals(sortMassive[0], "name")) {
                projectList = findProjectsBySortAndPage(
                        "name",
                        sortMassive[1],
                        0
                );
            }
        } else if (sort == null && page != null) {
            log.trace("Paging requested without sort: calculating offset {}", page * 10);
            projectList = projectRepository.findProjectsByPage(page * 10);
        } else if (sort != null && page != null) {
            String[] sortMassive = sort.split("_");
            log.trace("Sorting and paging requested: field='{}', direction='{}', page={}", sortMassive[0], sortMassive[1], page);

            if (Objects.equals(sortMassive[0], "date")) {
                projectList = findProjectsBySortAndPage(
                        "createDate",
                        sortMassive[1],
                        page
                );
            } else if (Objects.equals(sortMassive[0], "name")) {
                projectList = findProjectsBySortAndPage(
                        "name",
                        sortMassive[1],
                        page
                );
            }
        } else {
            log.trace("No parameters provided, defaulting to page 0 without sort.");
            projectList = projectRepository.findProjectsByPage(0);
        }

        if (projectList == null) {
            log.trace("No projects found (empty list), returning empty array.");
            return new InfoAnArrayProject[0];
        }

        log.trace("Found {} projects. Mapping to InfoAnArrayProject[].", projectList.size());
        return projectList.stream().map(
                project -> new InfoAnArrayProject(
                        project.getCreateDate(),
                        project.getName(),
                        project
                                .getTechnologies()
                                .stream()
                                .map(
                                        technology -> new TechnologyResponse(
                                                technology.getName()
                                        )
                                ).toArray(
                                        TechnologyResponse[]::new
                                )
                )
        ).toArray(InfoAnArrayProject[]::new);
    }

    /**
     * Изменение и сохранение нового проекта
     * @param projectRequest информация о проекте
     * @return название и статус измененного проекта
     */
    @Override
    public ProjectResponse updateProject(ProjectRequest projectRequest) {
        log.trace("Search project by name - {}", projectRequest.getName());
        Project project = projectRepository.getByName(projectRequest.getName());

        if (project == null) {
            log.error("The project not found - {}", projectRequest.getName());
            throw new RuntimeException("The project not found");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Update project by name - {}", projectRequest.getName());
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setShortDescription(projectRequest.getShortDescription());
        project.setUrl(projectRequest.getUrl());
        project.setUpdateDate(now);

        log.trace("Delete technologies project by name - {}", projectRequest.getName());
        project.setTechnologies(new HashSet<>());

        projectRequest.getTechnologyRequestList().forEach(technologyRequest -> {
            log.trace("Add technology in project - {}", technologyRequest.getName());
            project.addTechnology(
                    technologyService.createTechnology(technologyRequest.getName())
            );
        });

        log.trace("Save project by name - {}", projectRequest.getName());
        projectRepository.save(project);

        return new ProjectResponse(
                project.getName(),
                project.getStatus()
        );
    }

    /**
     * Вывод списка проектов по признакам
     * @param name название столбца
     * @param status статус сортировки
     * @param page номер страницы
     * @return список проектов
     */
    private List<Project> findProjectsBySortAndPage(String name, String status, Integer page) {
        log.trace("Preparing query: sort by '{}', direction '{}', page {}", name, status, page);

        if (SortVisibility.fromStatus(status) == SortVisibility.DOWN) {
            log.trace("Executing DOWN query (ASC) on field '{}' (Page: {})", name, page);
            Pageable pageable = PageRequest.of(page, 10, Sort.by(name).ascending());
            return projectRepository.findDownProjectsBySortAndPage(pageable);
        } else {
            log.trace("Executing UP query (DESC) on field '{}' (Page: {})", name, page);
            Pageable pageable = PageRequest.of(page, 10, Sort.by(name).descending());
            return projectRepository.findUpProjectsBySortAndPage(pageable);
        }
    }
}
