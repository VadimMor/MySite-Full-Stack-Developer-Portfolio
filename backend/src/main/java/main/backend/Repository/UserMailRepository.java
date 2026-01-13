package main.backend.Repository;

import jakarta.transaction.Transactional;
import main.backend.Entity.UserMail;
import main.backend.enums.StatusUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface UserMailRepository extends JpaRepository<UserMail, Long> {
    UserMail getByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserMail u WHERE " +
            "u.status = :statusUser AND " +
            "u.createDate < :now")
    void deleteAllByStatusAndDate(StatusUser statusUser, Timestamp now);

    Page<UserMail> findAllByStatus(Pageable pageable, StatusUser status);
}
