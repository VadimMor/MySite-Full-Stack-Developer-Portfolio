package main.backend.Service.Impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.backend.Entity.Technology;
import main.backend.Repository.TechnologyRepository;
import main.backend.Service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {
    @Autowired
    private TechnologyRepository technologyRepository;

    /**
     * Создание и сохранение новой технологии
     * @param name название технологии
     * @return сохраненная технология
     */
    @Override
    public Technology createTechnology(String name) {
        log.trace("Search technology by name - {}", name);
        Technology technology = technologyRepository.getByName(name);

        if (technology == null) {
            log.trace("Create new technology - {}", name);
            technology = new Technology(
                    name
            );
            technologyRepository.saveAndFlush(technology);
        }

        log.trace("Return technology - {}", technology.getName());
        return technology;
    }
}
