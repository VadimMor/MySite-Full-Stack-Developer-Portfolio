package blue_rabb.backend.service.Impl;

import blue_rabb.backend.dto.request.CreateSkillOrExperienceRequest;
import blue_rabb.backend.entity.Experience;
import blue_rabb.backend.repository.ExperienceRepository;
import blue_rabb.backend.service.ExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    private ExperienceRepository experienceRepository;

    /**
     * Метод создания переменной информации о опыте скилла и сохранение в бд
     * @param createSkillOrExperienceRequest информация о опыте
     */
    @Override
    public Experience createExperience(CreateSkillOrExperienceRequest createSkillOrExperienceRequest) {
        Experience experience = experienceRepository.getByNameAndDescription(createSkillOrExperienceRequest.getName(), createSkillOrExperienceRequest.getDescription());

        if (experience == null) {
            experience = new Experience(
                    createSkillOrExperienceRequest.getName(),
                    createSkillOrExperienceRequest.getDescription()
            );
            experienceRepository.saveAndFlush(experience);
        }

        return experience;
    }

    /**
     * Сохранение скилла в опыте в бд
     * @param experience опыт
     */
    @Override
    public void saveExperience(Experience experience) {
        experienceRepository.saveAndFlush(experience);
    }
}
