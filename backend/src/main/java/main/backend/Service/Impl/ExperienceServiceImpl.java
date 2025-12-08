package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Experience;
import main.backend.Entity.Skill;
import main.backend.Repository.ExperienceRepository;
import main.backend.Service.ExperienceService;
import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Response.ExperienceResponse;
import main.backend.enums.StatusVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    private ExperienceRepository experienceRepository;

    /**
     * Создает опыт и сохранеяет в бд
     * @param experienceRequest информация для создания и сохранения
     * @return статус о успешном создании опыта
     */
    @Override
    public ExperienceResponse createExperience(ExperienceRequest experienceRequest) {
        log.trace("Search experience by name - {}", experienceRequest.getName());
        Skill skill = experienceRepository.getByName(experienceRequest.getName());

        if (skill != null) {
            log.error("The experience has already been created - {}", experienceRequest.getName());
            throw new RuntimeException("The skill has already been created");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Create experience by name - {}", experienceRequest.getName());
        Experience newExperience = new Experience(
                experienceRequest.getName(),
                experienceRequest.getDescription(),
                now,
                now,
                StatusVisibility.DEVELOPMENT
        );

        log.trace("Save experience by name - {}", newExperience.getName());
        experienceRepository.saveAndFlush(newExperience);

        return new ExperienceResponse(
                newExperience.getName(),
                newExperience.getStatus()
        );
    }
}
