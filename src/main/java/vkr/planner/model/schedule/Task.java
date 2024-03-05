package vkr.planner.model.schedule;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @ExcelCellName("Задача")
    @Column(unique=true)
    private String type;
    @ExcelCellName("Номер")
    @Column
    private int order; // номинальный порядок
    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "task", orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Condition> conditions = new ArrayList<>();

    @ExcelCellName("Длительность")
    @Transient
    private Duration duration; // номинальная длительность в днях
    @Transient
    private Date date; // Дата
    @Transient
    private int costs; // издержки/затраты
    @Transient
    private String result; // результат проверки задачи

    public Task(Integer order, String type){
        this.order = order;
        this.type = type;
    }

}
