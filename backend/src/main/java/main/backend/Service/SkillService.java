package main.backend.Service;

import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Request.SkillRequest;
import main.backend.dto.Response.ExperienceResponse;
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
}
