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
public class Project extends ProjectPattern{ // Проект
    private Plan plan; // План проверок по проекту
    private List<Task> taskList; // Набор задач для данного проекта
    private boolean isValid; // Валидность проекта

    public static final String PROJECT = "Проект";
    public static final String TECHNICAL_DESCRIPTION = "Техническое описание";
    public Optional<Task> getTaskByType(TaskType taskType){ // Получаем задачу по ее типу
        return this.getTaskList().stream()
                .filter(task -> task.getTaskType().equals(taskType))
                .findFirst();
    }
}
