package vkr.planner.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tika.config.Field;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String type; // тип задачи

    @ManyToOne
    @JoinColumn(name="project_id")
    @JsonIgnore
    private Project project;

    @ManyToMany(mappedBy = "rule")
    private List<Rule> ruleList = new ArrayList<>();

    @Transient
    private Duration duration; // номинальная длительность в днях
    @Transient
    private Date date; // Дата
    @Transient
    private int costs; // издержки/затраты
    @Transient
    private String result; // результат проверки задачи

    public Task(String type, Date date){
        this.date = date;
        this.type = type;
    }
}
