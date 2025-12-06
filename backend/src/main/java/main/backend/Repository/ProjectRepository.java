package main.backend.Repository;

import main.backend.Entity.Project;
import main.backend.enums.StatusVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getByName(String name);

    Project getByNameAndStatus(String name, StatusVisibility statusVisibility);
}
