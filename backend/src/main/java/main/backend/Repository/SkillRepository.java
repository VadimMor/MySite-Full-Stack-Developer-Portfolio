package main.backend.Repository;

import main.backend.Entity.Skill;
import main.backend.enums.StatusVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill getByName(String name);

    List<Skill> findAllByStatus(StatusVisibility statusVisibility);
}
