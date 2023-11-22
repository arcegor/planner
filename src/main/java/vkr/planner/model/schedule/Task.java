package vkr.planner.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private TaskType taskType; // тип задачи
    private Duration duration; // номинальная длительность в днях
    private Date date;
    private int costs; // издержки/затраты
    private String result; // результат проверки задачи
}
