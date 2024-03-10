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
    private Project project;

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
}
