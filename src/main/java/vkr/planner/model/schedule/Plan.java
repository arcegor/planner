package vkr.planner.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    private String name;
    private List<Task> taskList;
    private boolean isValid;
    private Duration maxDuration; // в днях
    private int maxCosts;

    public Plan(String name){
        this.name = name;
    }

    public Optional<Task> getTaskByType(Task.TaskType taskType){
        return this.getTaskList().stream()
                .filter(task -> task.getTaskType().equals(taskType))
                .findFirst();
    }
}
