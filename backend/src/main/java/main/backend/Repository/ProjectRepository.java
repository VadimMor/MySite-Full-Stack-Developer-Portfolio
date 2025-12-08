package main.backend.Repository;

import main.backend.Entity.Project;
import main.backend.enums.StatusVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getByName(String name);

    Project getByNameAndStatus(String name, StatusVisibility statusVisibility);

    @Query(
            value = "SELECT * FROM project " +
                    "WHERE status = 'OPEN' " +
                    "LIMIT 10 OFFSET :page",
            nativeQuery = true
    )
    List<Project> findProjectsByPage(Integer page);

    @Query("SELECT pr FROM Project pr")
    List<Project> findDownProjectsBySortAndPage(Pageable pageable);

    @Query("SELECT pr FROM Project pr")
    List<Project> findUpProjectsBySortAndPage(Pageable pageable);
}
