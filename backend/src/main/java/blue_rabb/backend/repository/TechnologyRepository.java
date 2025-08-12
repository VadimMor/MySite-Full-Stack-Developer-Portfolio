package blue_rabb.backend.repository;

import blue_rabb.backend.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Technology getByName(String name);
}
