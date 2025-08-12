package blue_rabb.backend.service.Impl;

import blue_rabb.backend.entity.Technology;
import blue_rabb.backend.repository.TechnologyRepository;
import blue_rabb.backend.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {
    @Autowired
    private TechnologyRepository technologyRepository;

    @Override
    public Technology createTechnologyByGitHub(String name) {
        Technology technology = technologyRepository.getByName(name);

        if (technology != null) {
            return technology;
        }

        technology = new Technology(name);
        technologyRepository.saveAndFlush(technology);

        return technology;
    };
}
