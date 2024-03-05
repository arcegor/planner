package vkr.planner.model.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
}
