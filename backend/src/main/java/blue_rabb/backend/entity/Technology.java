package blue_rabb.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "technology")
@NoArgsConstructor
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Technology(String name) {
        this.name = name;
    }
}
