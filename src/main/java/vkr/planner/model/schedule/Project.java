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
public class Project { // Проект
    private ProjectType projectType; // Тип проекта
    private List<RuleType> ruleTypes; // Набор типов правил, из которых состоит тип проекта
    private List<Task> taskList; // Список задач в проекте
    private boolean isValid; // Валидность проекта
    private Duration maxDuration; // Длительноть работ по проекту
    private int maxCosts; // Издержки по проекту
    private TechnicalDescription technicalDescription; // набор характеристик (техническое описание проекта)

    public static final String PROJECT = "Проект";
    public static final String TECHNICAL_DESCRIPTION = "Техническое описание";
    public Optional<Task> getTaskByType(TaskType taskType){ // Получаем задание по его типу
        return this.getTaskList().stream()
                .filter(task -> task.getTaskType().equals(taskType))
                .findFirst();
    }
}
