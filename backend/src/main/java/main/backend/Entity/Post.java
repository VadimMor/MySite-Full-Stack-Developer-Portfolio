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
@Table(name = "post")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusVisibility status = StatusVisibility.DEVELOPMENT;

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String url;

    @Column
    private Integer length;

    @ManyToMany
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Post(Timestamp createDate, Timestamp updateDate, StatusVisibility status, String name, String description, String url, Integer length) {
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.name = name;
        this.description = description;
        this.url = url;
        this.length = length;
    }

    public void addCategorySet(Category category) {
        if (categories == null) categories = new HashSet<>();
        if (category != null) this.categories.add(category);
    }
}
