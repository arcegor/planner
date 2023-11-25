package vkr.planner.model.schedule;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    public String projectType;

    @OneToMany(mappedBy = "project",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public List<Task> taskTypes = new ArrayList<>();

    @OneToMany(mappedBy = "project",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public List<Rule> ruleTypes = new ArrayList<>();

    @Transient
    public TechnicalDescription technicalDescription;
}
