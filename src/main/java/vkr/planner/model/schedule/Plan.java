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
public class Plan { // Проект
    private PlanType planType; // Тип проекта
    private List<RuleType> ruleTypes; // Набор правил, из которых состоит проект
    private List<Task> taskList; // Список задач в проекте
    private boolean isValid; // Валидность проекта
    private Duration maxDuration; // Длительноть рабор по проекту
    private int maxCosts; // Издержки по проекту

    public static final String PLAN = "План";
    public static final String TECHNICAL_DESCRIPTION = "Техническое описание";
    public Optional<Task> getTaskByType(TaskType taskType){ // Получаем задание по его типу
        return this.getTaskList().stream()
                .filter(task -> task.getTaskType().equals(taskType))
                .findFirst();
    }
}
