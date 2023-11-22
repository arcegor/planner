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
    private Plan plan; // План проверок по проекту
    private List<RuleType> ruleTypes; // Набор типов правил для данного типа проекта
    private List<Task> taskList; // Набор задач для данного типа проекта
    private boolean isValid; // Валидность проекта
    private Duration maxDuration; // Длительноть работ по проекту
    private int maxCosts; // Издержки по проекту
    private TechnicalDescription technicalDescription; // набор характеристик (техническое описание проекта)

    public static final String PROJECT = "Проект";
    public static final String TECHNICAL_DESCRIPTION = "Техническое описание";
    public Optional<Task> getTaskByType(TaskType taskType){ // Получаем задачу по ее типу
        return this.getTaskList().stream()
                .filter(task -> task.getTaskType().equals(taskType))
                .findFirst();
    }
}
