package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.Plan;
import vkr.planner.model.db.Condition;
import vkr.planner.model.db.Task;
import vkr.planner.service.RuleService;

@Component
public class RuleServiceImplTemp implements RuleService {
    public static final String RULE_TYPE = "Температура воды";
    @Override
    public void applyRule(Plan plan, Condition condition) {
        Task task = plan.getTaskByCondition(condition);
        int value = Integer.parseInt(condition.getValue());

        if (task.getPresentedByPlan()) {
            if (value == 100) {
                task.setProvidedByRule(true);
                task.setStatus(String.format("Повтор задачи '%s' по нагреванию воды! Вода уже вскипячена!", task.getType()));
            }
            else {
                task.setStatus(String.format("Температура воды '%s' недостаточна для заварки чая!", condition.getValue()));
                task.setProvidedByRule(false);
            }
        }
        else {
            if (value == 100) {
                task.setProvidedByRule(true);
                task.setStatus(String.format("Задача '%s' выполнена по дополнительному условию!", task.getType()));
            }
            else {
                task.setStatus(String.format("Температура воды '%s' недостаточна для заварки чая!", condition.getValue()));                task.setProvidedByRule(false);
            }
        }
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
