package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.Plan;
import vkr.planner.model.db.Condition;
import vkr.planner.model.db.Task;
import vkr.planner.service.RuleService;

@Component
public class RuleServiceImplTemp implements RuleService {
    public static final String RULE_TYPE = "Температура воды";
    public static final String DUPLICATED_TASK = "Повтор задачи по нагреванию воды! Вода уже вскипячена!";
    public static final String TASK_COMPLETED_BY_CONDITION = "Задача выполнена по дополнительному условию!";
    public static final String NOT_ENOUGH_TO_PERFORM_TASK = "Температура воды недостаточна для заварки чая!";
    @Override
    public void applyRule(Plan plan, Condition condition) {
        Task task = plan.getTaskByCondition(condition);
        int value = Integer.parseInt(condition.getValue());
        if (task.getPresentedByPlan()) {
            if (value == 100) {
                task.setProvidedByRule(true);
                task.setStatus(DUPLICATED_TASK);
            }
            else {
                task.setStatus(NOT_ENOUGH_TO_PERFORM_TASK);
                task.setProvidedByRule(false);
            }
        }
        else {
            if (value == 100) {
                task.setProvidedByRule(true);
                task.setStatus(TASK_COMPLETED_BY_CONDITION);
            }
            else {
                task.setStatus(NOT_ENOUGH_TO_PERFORM_TASK);
                task.setProvidedByRule(false);
            }
        }
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
