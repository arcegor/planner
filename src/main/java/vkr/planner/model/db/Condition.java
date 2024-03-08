package vkr.planner.model.db;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ExcelCellName("Условие")
    @Column(unique=true)
    private String type;

    @ManyToOne
    private Task task;

    @Column
    private String description;

    @ExcelCellName("Значение")
    @Transient
    private String value;

    @Transient
    private Boolean result;

    @Transient
    private Boolean isPresentByPlan; // Есть ли условие в проверяемом плане
    public Condition(String type, String value){
        this.type = type;
        this.value = value;
    }
    public Condition getConditionByType(List<Condition> conditions, String type){
        return conditions.stream()
                .filter(condition -> condition.getType().equals(type))
                .findFirst()
                .orElse(null);
    }
    public boolean isConditionIncludingByTask(Task task){
        return this.task.getType().equals(task.getType());
    }
}
