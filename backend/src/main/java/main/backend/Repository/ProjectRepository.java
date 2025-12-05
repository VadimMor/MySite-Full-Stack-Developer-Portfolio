package main.backend.Repository;

import main.backend.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getByNameAndStatus(String name, boolean b);
}
