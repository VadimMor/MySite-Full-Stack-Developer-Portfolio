package blue_rabb.backend.service.Impl;

import blue_rabb.backend.dto.request.CreateSkillOrExperienceRequest;
import blue_rabb.backend.dto.response.ExperienceResponse;
import blue_rabb.backend.dto.response.SkillResponse;
import blue_rabb.backend.entity.Experience;
import blue_rabb.backend.entity.Skill;
import blue_rabb.backend.repository.SkillRepository;
import blue_rabb.backend.service.ExperienceService;
import blue_rabb.backend.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    private ExperienceService experienceService;

    private SkillRepository skillRepository;

    /**
     * Метод создания переменной информации о скилле и сохранение в бд
     * @param createSkillOrExperienceRequest информация о скилле
     */
    @Override
    public void createSkill(CreateSkillOrExperienceRequest createSkillOrExperienceRequest) {
        Skill skill = skillRepository.getByName(createSkillOrExperienceRequest.getName());

        if (skill != null) {
            throw new RuntimeException();
        }

        skill = new Skill(
                createSkillOrExperienceRequest.getName(),
                createSkillOrExperienceRequest.getDescription()
        );
        skillRepository.saveAndFlush(skill);
    }

    /**
     * Метод создания переменной информации о опыте скилла и сохранение в бд
     * @param createSkillOrExperienceRequest информация о опыте
     */
    @Override
    public void createExperience(CreateSkillOrExperienceRequest createSkillOrExperienceRequest, String nameSkill) {
        Skill skill = skillRepository.getByName(nameSkill);

        if (skill == null) {
            throw new RuntimeException();
        }

        Experience experience = experienceService.createExperience(createSkillOrExperienceRequest);
        experience.setSkill(skill);
        experienceService.saveExperience(experience);

        skillRepository.saveAndFlush(skill);
    }

    /**
     * Вывод массива информации о скиллов
     * @return массив информации
     */
    @Override
    public SkillResponse[] getMassiveSkill() {
        List<Skill> skillList = skillRepository.getAllWithExperiences();

        return skillList.stream()
                .map(skill -> new SkillResponse(
                        skill.getName(),
                        skill.getDescription(),
                        skill.getExperiences().stream()
                                .map(experience -> new ExperienceResponse(
                                        experience.getName(),
                                        experience.getDescription()
                                ))
                                .toArray(ExperienceResponse[]::new)
                ))
                .toArray(SkillResponse[]::new);
    }
}
