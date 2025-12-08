package main.backend.Service;

import main.backend.dto.Request.ProjectRequest;
import main.backend.dto.Request.UpdateStatusProject;
import main.backend.dto.Response.FullInfoProjectResponse;
import main.backend.dto.Response.InfoAnArrayProject;
import main.backend.dto.Response.ProjectResponse;
import main.backend.dto.Response.ShortInfoProjectResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    /**
     * Создание и сохранение нового проекта
     * @param projectRequest информация о проекте
     * @return название и статус созданного проекта
     */
    ProjectResponse createProject(ProjectRequest projectRequest);

    /**
     * Обновление статуса проекта
     * @param updateStatusProject информация для обновления
     * @return обновленный статус проекта
     */
    ProjectResponse updateStatus(UpdateStatusProject updateStatusProject);

    /**
     * Вывод полной информации о проекте
     * @param name название проекта
     * @return информация о проекте
     */
    FullInfoProjectResponse getProject(String name);

    /**
     * Вывод краткой информации о проекте
     * @param name название проекта
     * @return информация о проекте
     */
    ShortInfoProjectResponse getShortProject(String name);

    /**
     * Вывод массива проектов по сортировке
     * @param sort сортировка по признаку
     * @param page страница для вывода проектов
     * @return массив проектов
     */
    InfoAnArrayProject[] getMassiveProjects(String sort, Integer page);

    /**
     * Изменение и сохранение нового проекта
     * @param projectRequest информация о проекте
     * @return название и статус измененного проекта
     */
    ProjectResponse updateProject(ProjectRequest projectRequest);
}
