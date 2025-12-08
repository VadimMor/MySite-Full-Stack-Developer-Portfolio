package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Skill;
import main.backend.Repository.SkillRepository;
import main.backend.Service.ExperienceService;
import main.backend.Service.SkillService;
import main.backend.dto.Request.ExperienceRequest;
import main.backend.dto.Request.SkillRequest;
import main.backend.dto.Request.UpdateStatusExperience;
import main.backend.dto.Request.UpdateStatusSkill;
import main.backend.dto.Response.*;
import main.backend.enums.StatusVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ExperienceService experienceService;

    /**
     * Создает скилл и сохранеяет в бд
     * @param skillRequest информация для создания и сохранения
     * @return статус о успешном создании скилла
     */
    @Override
    public SkillResponse createSkill(SkillRequest skillRequest) {
        log.trace("Search skill by name - {}", skillRequest.getName());
        Skill skill = skillRepository.getByName(skillRequest.getName());

        if (skill != null) {
            log.error("The skill has already been created - {}", skillRequest.getName());
            throw new RuntimeException("The skill has already been created");
        }

        log.trace("Create now timestamp");
        Timestamp now = Timestamp.from(Instant.now());

        log.trace("Create skill by name - {}", skillRequest.getName());
        Skill newSkill = new Skill(
                skillRequest.getName(),
                skillRequest.getDescription(),
                now,
                now,
                StatusVisibility.DEVELOPMENT
        );

        log.trace("Save skill by name - {}", newSkill.getName());
        skillRepository.saveAndFlush(newSkill);

        return new SkillResponse(
                newSkill.getName(),
                newSkill.getStatus()
        );
    }

    /**
     * Создает опыт и сохранеяет в бд
     * @param experienceRequest информация для создания и сохранения
     * @return статус о успешном создании опыта
     */
    @Override
    public ExperienceResponse createExperience(ExperienceRequest experienceRequest) {
        log.trace("Create experience by name - {}", experienceRequest.getName());
        return experienceService.createExperience(experienceRequest);
    }

    /**
     * Вывод массива скиллов с опытом
     * @return массив скиллов
     */
    @Override
    public FullSkillResponse[] getMassiveSkill() {
        log.trace("Search all skill");
        List<Skill> skillList = skillRepository.findAllByStatus(StatusVisibility.OPEN);

        log.trace("Found {} skills. Mapping to FullSkillResponse[].", skillList.size());
        return skillList.stream().map(
                skill -> new FullSkillResponse(
                        skill.getName(),
                        skill.getDescription(),
                        skill.getExperiences()
                                .stream()
                                .map(
                                        experience -> new FullExperienceResponse(
                                                experience.getName(),
                                                experience.getDescription()
                                        )
                                ).toArray(
                                        FullExperienceResponse[]::new
                                )
                )
        ).toArray(FullSkillResponse[]::new);
    }

    /**
     * Обновление статуса скилла
     * @param updateStatusSkill информация для обновления
     * @return статус о успешном обновлении
     */
    @Override
    public SkillResponse updateStatusSkill(UpdateStatusSkill updateStatusSkill) {
        log.trace("Search skill by name - {}", updateStatusSkill.getName());
        Skill skill = skillRepository.getByName(updateStatusSkill.getName());

        if (skill == null) {
            log.error("The skill not found - {}", updateStatusSkill.getName());
            throw new RuntimeException("The skill not found");
        }

        log.trace("Update status \"{}\" skill - {}", updateStatusSkill.getStatus(), updateStatusSkill.getName());
        skill.setStatus(
                StatusVisibility.fromStatus(updateStatusSkill.getStatus())
        );

        log.trace("Save skill by name - {}", skill.getName());
        skillRepository.save(skill);

        return new SkillResponse(
                skill.getName(),
                skill.getStatus()
        );
    }

    /**
     * Обновление статуса опыта
     * @param updateStatusExperience информация для обновления
     * @return статус о успешном обновлении
     */
    @Override
    public ExperienceResponse updateStatusExperience(UpdateStatusExperience updateStatusExperience) {
        return experienceService.updateStatusExperience(updateStatusExperience);
    }
}
