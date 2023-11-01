package vkr.planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String name;
    private int order; // в контексте плана
    private Duration duration; // номинальная длительность в днях
    private boolean isBlocker; // является ли блокером в плане (означает, что пока не будет выполнено,
                               // следующие по порядку задачи начинать нельзя)
    private int costs; // издержки/затраты
    public Task(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", duration=" + duration +
                ", isBlocker=" + isBlocker +
                ", costs=" + costs +
                '}';
    }
}
