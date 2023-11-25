package vkr.planner.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Rule {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String type;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name="project_id")
    @JsonIgnore
    private Project project;

    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "rule_task",
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> task = new ArrayList<>();
}
