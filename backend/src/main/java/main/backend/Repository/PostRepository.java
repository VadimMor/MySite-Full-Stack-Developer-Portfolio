package main.backend.Repository;

import main.backend.Entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post getByName(String name);

    @Query("SELECT p FROM Post p")
    List<Post> findDownPostsBySortAndPage(Pageable pageable);

    @Query("SELECT p FROM Post p")
    List<Post> findUpPostsBySortAndPage(Pageable pageable);

    @Query(
            value = "SELECT * FROM post " +
                    "WHERE status = 'OPEN' " +
                    "LIMIT 10 OFFSET :page",
            nativeQuery = true
    )
    List<Post> findPostsByPage(Integer page);
}
