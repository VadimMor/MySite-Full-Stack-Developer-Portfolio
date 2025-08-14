package blue_rabb.backend.repository;

import blue_rabb.backend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill getByName(String name);

    @Query("SELECT s FROM Skill s LEFT JOIN FETCH s.experiences")
    List<Skill> getAllWithExperiences();
}
