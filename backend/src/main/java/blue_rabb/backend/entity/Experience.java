package blue_rabb.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "experience")
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Skill skill;

    public Experience(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
