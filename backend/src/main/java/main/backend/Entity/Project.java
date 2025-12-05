package main.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "project")
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column
    private Boolean status;

    @Column
    private String name;

    @Column(name = "short_description", length = 2000)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String url;

    @ManyToMany
    @JoinTable(
            name = "projects_technologies",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<Technology> technologies = new HashSet<>();

    public Project(Timestamp createDate, Timestamp updateDate, Boolean status, String name, String shortDescription, String description, String url) {
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.url = url;
    }

    public void addTechnology(Technology technology) {
        this.technologies.add(technology);
    }
}
