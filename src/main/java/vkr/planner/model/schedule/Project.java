package vkr.planner.model.schedule;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique=true)
    public String type;

    @OneToMany(mappedBy = "project", orphanRemoval = true,
            cascade = CascadeType.ALL)
    public List<Task> tasks = new ArrayList<>();

    @Transient
    public TechnicalDescription technicalDescription;
}
