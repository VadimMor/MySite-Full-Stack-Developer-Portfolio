package main.backend.Service;

import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Response.ExperienceResponse;
import org.springframework.stereotype.Service;

@Service
public interface ExperienceService {
    /**
     * Создает опыт и сохранеяет в бд
     * @param experienceRequest информация для создания и сохранения
     * @return статус о успешном создании опыта
     */
    ExperienceResponse createExperience(ExperienceRequest experienceRequest);
}
