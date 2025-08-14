package blue_rabb.backend.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "date_create_project")
    private Date dateCreateProject;

    @Column(name = "date_update_project")
    private Date dateUpdateProject;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_update")
    private Date dateUpdate;

    @ManyToMany
    @JoinTable(
            name = "projects_technologies",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<Technology> technologies = new HashSet<>();

    public Project(String name, String description, Date dateCreateProject, Date dateUpdateProject, Date dateCreate, Date dateUpdate, Set<Technology> technologies) {
        this.name = name;
        this.description = description;
        this.dateCreateProject = dateCreateProject;
        this.dateUpdateProject = dateUpdateProject;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.technologies = technologies;
    }
}
