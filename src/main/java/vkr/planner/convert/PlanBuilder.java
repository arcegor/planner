package vkr.planner.convert;


import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.RequestProject;
import vkr.planner.model.schedule.Rule;
import vkr.planner.model.schedule.Task;

import java.util.List;
import java.util.Map;

@Component
public class PlanBuilder {

    public Plan build(Map<String, Object> ruleSet, RequestProject requestProject) {
        Plan plan = new Plan();

        plan.setTaskList(requestProject.getRequestTasks());

        plan.getTaskList().stream()
                .filter(task -> task.getRules()
                        .stream()
                        .anyMatch(rule -> ruleSet.containsKey(rule.getType())))
                .forEach(task -> task.getRules().removeIf(rule -> !ruleSet.containsKey(rule.getType())));

        plan.getTaskList()
                .forEach(task -> task.getRules()
                        .forEach(rule -> plan.getParams().put(rule.getType(), ruleSet.get(rule.getType()))));

        if (plan.getTaskList().isEmpty())
            plan.setIsEmpty(Boolean.TRUE);
        else plan.setIsEmpty(Boolean.FALSE);

        return plan;
    }
}
