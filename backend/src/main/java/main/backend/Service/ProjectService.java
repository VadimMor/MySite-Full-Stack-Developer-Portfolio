package main.backend.Service;

import main.backend.dto.Request.ProjectRequest;
import main.backend.dto.Request.UpdateStatusProject;
import main.backend.dto.Response.ProjectResponse;
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
}
