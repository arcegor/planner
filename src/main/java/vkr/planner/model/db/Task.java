package vkr.planner.model.db;

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

    @Column(name = "task_order")
    private int order; // номинальный порядок

    @ExcelCellName("Номер")
    @Transient
    private int orderByPlan; // Порядок в рамках плана

    @ManyToOne
    private Project project;

    @ExcelCellName("Длительность")
    @Transient
    private Duration duration; // номинальная длительность в днях

    @Transient
    private Date date; // Дата

    @Transient
    private int costs; // издержки/затраты

    @Transient
    private Boolean presentedByPlan = false; // Есть ли задача в проверяемом плане

    @Transient
    private String status; // статус задачи в плане

    @Transient
    private boolean providedByRule = false; // Обеспечивает ли переданное условие выполнение задачи

    @Transient
    private boolean completable; // Основная функция задачи

    public Task(Integer order, String type){
        this.order = order;
        this.type = type;
    }
    public boolean containsInTasks(List<Task> tasks){
        return tasks.stream()
                .map(Task::getType)
                .anyMatch(type -> this.getType().equals(type));
    }
    @Override
    public boolean equals(Object task){
        if (this == task)
            return true;
        if (task == null || task.getClass() != this.getClass()) {
            return false;
        }
        return this.getType().equals(((Task) task).getType());
    }
}
