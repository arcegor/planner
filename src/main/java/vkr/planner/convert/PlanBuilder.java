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
        List<Rule> ruleList = requestProject.getRuleTypes();
        List<Task> taskList = requestProject.getTaskList();
        for (Task task: taskList){
            if (task.getRuleList().isEmpty()) continue;
            for (Rule rule: task.getRuleList()){
                if (ruleSet.containsKey(rule.getType()) && ruleList.contains(rule)){
                    if (!plan.getRuleList().contains(rule)){
                        plan.getRuleList().add(rule);
                        plan.getParams().put(rule.getType(), ruleSet.get(rule.getType()));
                    }
                }
            }
        }
        if (plan.getRuleList().isEmpty())
            plan.setIsEmpty(Boolean.TRUE);
        else plan.setIsEmpty(Boolean.FALSE);
        return plan;
    }
}
