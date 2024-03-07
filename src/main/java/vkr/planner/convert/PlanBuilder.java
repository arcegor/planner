package vkr.planner.convert;


import org.springframework.stereotype.Component;
import vkr.planner.model.db.Condition;
import vkr.planner.model.Plan;
import vkr.planner.model.db.Task;

import java.util.List;

@Component
public class PlanBuilder {
    public Plan build(List<Condition> conditions, List<Task> tasks) {
        Plan plan = new Plan();

        for (Task task: tasks){
            for (Condition condition: conditions){
                if (condition.isConditionIncludingByTask(task))
                    task.getConditions().add(condition);
            }
        }

        plan.setTasks(tasks);
        plan.setIsEmpty(tasks.isEmpty());
        return plan;
    }
}
