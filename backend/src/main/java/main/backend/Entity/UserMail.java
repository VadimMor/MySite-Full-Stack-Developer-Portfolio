package main.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.backend.enums.StatusUser;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_mail")
@NoArgsConstructor
public class UserMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusUser status = StatusUser.CREATED;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    public UserMail(String name, String email, StatusUser status, Timestamp createDate, Timestamp updateDate) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
