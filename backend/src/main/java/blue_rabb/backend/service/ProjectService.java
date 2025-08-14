package blue_rabb.backend.service;


import blue_rabb.backend.dto.response.FullInfoProjectResponse;
import blue_rabb.backend.dto.response.MassiveProjectsResponse;
import blue_rabb.backend.dto.response.ProjectCreateResponse;
import blue_rabb.backend.dto.response.ProjectResponse;

public interface ProjectService  {
    /**
     * Метод создания проекта и сохранения в бд
     * @param name название репозитория
     * @return статус сохранения
     */
    ProjectCreateResponse createProject(String name);

    MassiveProjectsResponse getProjects(Integer page);

    FullInfoProjectResponse getProject(String name);
}
