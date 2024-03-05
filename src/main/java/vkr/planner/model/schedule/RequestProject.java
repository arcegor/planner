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

    public Plan plan = new Plan(); // План проверок по проекту

    public boolean isValid; // Валидность проекта
    public boolean contains(Task task){
        return plan.getTasks().stream()
                .anyMatch(t -> t.getType().equals(task.getType()));
    }
}
