package vkr.planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String name; // наименование
    private int order; // в контексте плана
    private Duration duration; // номинальная длительность
    private boolean isBlocker; // является ли блокером в плане (означает, что пока не будет выполнено,
                               // следующие по порядку задачи начинать нельзя)
    private int costs; // издержки/затраты

    public Task(String name){
        this.name = name;
    }

}
