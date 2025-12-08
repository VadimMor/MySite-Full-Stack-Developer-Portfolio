package main.backend.Repository;

import main.backend.Entity.Experience;
import main.backend.Entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Experience getByName(String name);
}
