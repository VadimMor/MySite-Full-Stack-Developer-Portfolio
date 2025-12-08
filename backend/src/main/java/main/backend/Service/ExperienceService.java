package main.backend.Service;

import main.backend.Entity.Experience;
import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Request.UpdateStatusExperience;
import main.backend.dto.Response.ExperienceResponse;
import main.backend.dto.Response.FullExperienceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperienceService {
    /**
     * Создает опыт и сохранеяет в бд
     * @param experienceRequest информация для создания и сохранения
     * @return статус о успешном создании опыта
     */
    ExperienceResponse createExperience(ExperienceRequest experienceRequest);

    /**
     * Обновление статуса опыта
     * @param updateStatusExperience информация для обновления
     * @return статус о успешном обновлении
     */
    ExperienceResponse updateStatusExperience(UpdateStatusExperience updateStatusExperience);

    /**
     * Получения списка опыта по названиям
     * @param experiences название опыта
     * @return список опыта
     */
    List<Experience> getAllByNames(List<String> experiences);

    /**
     * Вывод массива опыта
     * @return массив опыта
     */
    FullExperienceResponse[] getMassiveExperience();
}
