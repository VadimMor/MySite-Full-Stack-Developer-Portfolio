package blue_rabb.backend.service;

import blue_rabb.backend.dto.request.CreateSkillOrExperienceRequest;
import blue_rabb.backend.entity.Experience;

public interface ExperienceService {
    /**
     * Метод создания переменной информации о опыте скилла и сохранение в бд
     * @param createSkillOrExperienceRequest информация о опыте
     */
    Experience createExperience(CreateSkillOrExperienceRequest createSkillOrExperienceRequest);

    /**
     * Сохранение скилла в опыте в бд
     * @param experience опыт
     */
    void saveExperience(Experience experience);
}
