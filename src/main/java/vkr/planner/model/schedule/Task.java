package vkr.planner.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.tika.config.Field;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique=true)
    private String type;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "task", orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Rule> rules = new ArrayList<>();

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
