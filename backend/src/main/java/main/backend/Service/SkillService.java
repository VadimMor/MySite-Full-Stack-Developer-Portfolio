package main.backend.Service;

import main.backend.dto.Request.*;
import main.backend.dto.Response.ExperienceResponse;
import main.backend.dto.Response.FullExperienceResponse;
import main.backend.dto.Response.FullSkillResponse;
import main.backend.dto.Response.SkillResponse;
import org.springframework.stereotype.Service;

@Service
public interface SkillService {
    /**
     * Создает скилл и сохранеяет в бд
     * @param skillRequest информация для создания и сохранения
     * @return статус о успешном создании скилла
     */
    SkillResponse createSkill(SkillRequest skillRequest);

    /**
     * Создает опыт и сохранеяет в бд
     * @param experienceRequest информация для создания и сохранения
     * @return статус о успешном создании опыта
     */
    ExperienceResponse createExperience(ExperienceRequest experienceRequest);

    /**
     * Вывод массива скиллов с опытом
     * @return массив скиллов
     */
    FullSkillResponse[] getMassiveSkill();

    /**
     * Обновление статуса скилла
     * @param updateStatusSkill информация для обновления
     * @return статус о успешном обновлении
     */
    SkillResponse updateStatusSkill(UpdateStatusSkill updateStatusSkill);

    /**
     * Обновление статуса опыта
     * @param updateStatusExperience информация для обновления
     * @return статус о успешном обновлении
     */
    ExperienceResponse updateStatusExperience(UpdateStatusExperience updateStatusExperience);

    /**
     * Добавление массива опыта к скиллу
     * @param addExperienceInSkill информация для добавления
     * @return статус о успешном добавлении
     */
    SkillResponse addExperienceInSkill(AddExperienceInSkillRequest addExperienceInSkill);

    /**
     * Вывод массива опыта
     * @return массив опыта
     */
    FullExperienceResponse[] getMassiveExperience();
}
