package blue_rabb.backend.repository;

import blue_rabb.backend.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Experience getByNameAndDescription(String name, String description);
}
