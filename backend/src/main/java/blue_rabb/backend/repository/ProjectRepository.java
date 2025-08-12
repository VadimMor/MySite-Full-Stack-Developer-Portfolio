package blue_rabb.backend.repository;

import blue_rabb.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getByName(String name);

    @Query("SELECT p FROM Project p " +
            "ORDER BY p.dateCreate DESC " +
            "LIMIT 20 OFFSET :page")
    List<Project> getListByPage(Integer page);

    @Query("SELECT COUNT(p) FROM Project p")
    Long getTotalProjectsCount();
}
