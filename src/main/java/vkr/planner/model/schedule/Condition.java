package vkr.planner.model.schedule;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

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
    private String result;
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
}
