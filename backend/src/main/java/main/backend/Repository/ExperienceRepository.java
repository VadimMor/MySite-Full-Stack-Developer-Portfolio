package main.backend.Repository;

import main.backend.Entity.Experience;
import main.backend.Entity.Skill;
import main.backend.enums.StatusVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Experience getByName(String name);

    Experience getByNameAndStatus(String name, StatusVisibility statusVisibility);

    List<Experience> findAllByStatus(StatusVisibility statusVisibility);
}
