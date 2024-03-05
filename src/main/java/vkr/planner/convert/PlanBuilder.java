package vkr.planner.convert;


import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Condition;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.RequestProject;
import vkr.planner.model.schedule.Task;

import java.util.List;
import java.util.Map;

@Component
public class PlanBuilder {
    public Plan build(List<Condition> conditions, List<Task> tasks) {
        Plan plan = new Plan();
        plan.setTasks(tasks);
        plan.setConditions(conditions);
        plan.setIsEmpty(tasks.isEmpty());
        return plan;
    }
}
