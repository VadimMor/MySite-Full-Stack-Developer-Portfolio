package blue_rabb.backend.service;

import blue_rabb.backend.dto.request.CreateSkillOrExperienceRequest;
import blue_rabb.backend.dto.response.SkillResponse;

public interface SkillService {
    /**
     * Метод создания переменной информации о скилле и сохранение в бд
     * @param createSkillOrExperienceRequest информация о скилле
     */
    void createSkill(CreateSkillOrExperienceRequest createSkillOrExperienceRequest);

    /**
     * Метод создания переменной информации о опыте скилла и сохранение в бд
     * @param createSkillOrExperienceRequest информация о опыте
     */
    void createExperience(CreateSkillOrExperienceRequest createSkillOrExperienceRequest, String nameSkill);

    /**
     * Вывод массива информации о скиллов
     * @return массив информации
     */
    SkillResponse[] getMassiveSkill();
}
