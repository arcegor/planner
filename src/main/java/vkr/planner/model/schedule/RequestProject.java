package vkr.planner.model.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class RequestProject extends Project{ // Проект
    public RequestProject(){
        super();
    }
    public Plan plan; // План проверок по проекту
    public List<Task> requestTasks; // Набор задач для данного проекта
    public boolean isValid; // Валидность проекта

    public boolean contains(Task task){
        return requestTasks.stream()
                .anyMatch(t -> t.getType().equals(task.getType()));
    }
}
