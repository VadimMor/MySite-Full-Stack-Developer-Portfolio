package main.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.backend.enums.StatusVisibility;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusVisibility status = StatusVisibility.DEVELOPMENT;

    public Category(Timestamp createDate, Timestamp updateDate, String name, StatusVisibility status) {
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.name = name;
        this.status = status;
    }
}
