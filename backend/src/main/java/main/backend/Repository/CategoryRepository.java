package main.backend.Repository;

import main.backend.Entity.Category;
import main.backend.enums.StatusVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getByName(String name);

    List<Category> findAllByStatus(StatusVisibility statusVisibility);
}
