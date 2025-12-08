package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Experience;
import main.backend.Entity.Skill;
import main.backend.Repository.ExperienceRepository;
import main.backend.Service.ExperienceService;
import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Request.UpdateStatusExperience;
import main.backend.dto.Response.ExperienceResponse;
import main.backend.dto.Response.FullExperienceResponse;
import main.backend.dto.Response.SkillResponse;
import main.backend.enums.StatusVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
        Experience experience = experienceRepository.getByName(experienceRequest.getName());

        if (experience != null) {
            log.error("The experience has already been created - {}", experienceRequest.getName());
            throw new RuntimeException("The experience has already been created");
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

    /**
     * Обновление статуса опыта
     * @param updateStatusExperience информация для обновления
     * @return статус о успешном обновлении
     */
    @Override
    public ExperienceResponse updateStatusExperience(UpdateStatusExperience updateStatusExperience) {
        log.trace("Search experience by name - {}", updateStatusExperience.getName());
        Experience experience = experienceRepository.getByName(updateStatusExperience.getName());

        if (experience == null) {
            log.error("The experience not found - {}", updateStatusExperience.getName());
            throw new RuntimeException("The experience not found");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Update status \"{}\" experience - {}", updateStatusExperience.getStatus(), updateStatusExperience.getName());
        experience.setStatus(
                StatusVisibility.fromStatus(updateStatusExperience.getStatus())
        );
        experience.setUpdateDate(now);

        log.trace("Save experience by name - {}", experience.getName());
        experienceRepository.save(experience);

        return new ExperienceResponse(
                experience.getName(),
                experience.getStatus()
        );
    }

    /**
     * Получения списка опыта по названиям
     * @param experiences название опыта
     * @return список опыта
     */
    @Override
    public List<Experience> getAllByNames(List<String> experiences) {
        log.trace("Found {} experiences", experiences.size());
        List<Experience> experienceList = new ArrayList<>();

        experiences.forEach(name -> {
            log.trace("Search experience by name - {}", name);
            Experience experience = experienceRepository.getByNameAndStatus(name, StatusVisibility.OPEN);

            log.trace("Add list experience by name - {}", name);
            if (experience != null) {
                experienceList.add(experience);
            }
        });

        return experienceList;
    }

    /**
     * Вывод массива опыта
     * @return массив опыта
     */
    @Override
    public FullExperienceResponse[] getMassiveExperience() {
        log.trace("Search all experience");
        List<Experience> experienceList = experienceRepository.findAllByStatus(StatusVisibility.OPEN);

        log.trace("Found {} experiences. Mapping to FullExperienceResponse[].", experienceList.size());
        return experienceList.stream().map(
                experience -> new FullExperienceResponse(
                        experience.getName(),
                        experience.getDescription()
                )
        ).toArray(FullExperienceResponse[]::new);
    }
}
