package main.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.backend.enums.StatusVisibility;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "skill")
@NoArgsConstructor
public class Skill {
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

    @ManyToMany
    @JoinTable(
            name = "skill_experience",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id")
    )
    private Set<Experience> experiences = new HashSet<>();

    public Skill(String name, String description, Timestamp createDate, Timestamp updateDate, StatusVisibility status) {
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public void addExperience(Experience experience) {
        this.experiences.add(experience);
    }
}
