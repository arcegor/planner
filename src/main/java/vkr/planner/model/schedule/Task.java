package vkr.planner.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String name;
    private TaskType taskType;
    private int order; // в контексте плана
    private Duration duration; // номинальная длительность в днях
    private boolean isBlocker; // является ли блокером в плане (означает, что пока не будет выполнено,
                               // следующие по порядку задачи начинать нельзя)
    private int costs; // издержки/затраты
    private boolean isDone; // выполнено или нет
    public Task(String name){
        this.name = name;
    }
    public Task(String string, int i, Duration duration, boolean b, int i1) {
    }

    enum TaskType{
        THERMAL_INSULATION,
        ENCAPSULATION
    }
}
