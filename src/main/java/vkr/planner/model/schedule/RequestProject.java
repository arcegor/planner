package vkr.planner.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProject extends Project{ // Проект
    private Plan plan; // План проверок по проекту
    private List<Task> taskList; // Набор задач для данного проекта
    private boolean isValid; // Валидность проекта

    public RequestProject(String projectType){
        this.projectType = projectType;
    }
    public Optional<Task> getTaskByType(String taskType){ // Получаем задачу по ее типу
        return this.getTaskList().stream()
                .filter(task -> task.getType().equals(taskType))
                .findFirst();
    }
}
