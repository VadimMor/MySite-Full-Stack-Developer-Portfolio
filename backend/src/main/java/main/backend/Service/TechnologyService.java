package main.backend.Service;

import main.backend.Entity.Technology;
import org.springframework.stereotype.Service;

@Service
public interface TechnologyService {
    /**
     * Создание и сохранение новой технологии
     * @param name название технологии
     * @return сохраненная технология
     */
    public Technology createTechnology(String name);
}
