package vkr.planner.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private TaskType taskType; // тип задачи
    private Duration duration; // номинальная длительность в днях
    private int costs; // издержки/затраты
    private boolean isDone; // выполнено или нет
    private String result; // результат проверки задачи
    public void setDone(String done){
        this.isDone = done.contains("да");
    }
}
