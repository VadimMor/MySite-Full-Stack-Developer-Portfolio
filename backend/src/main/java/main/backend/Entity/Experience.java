package main.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.backend.enums.StatusVisibility;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "experience")
@NoArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusVisibility status = StatusVisibility.DEVELOPMENT;

    public Experience(String name, String description, Timestamp createDate, Timestamp updateDate, StatusVisibility status) {
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }
}
