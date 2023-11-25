package vkr.planner.service.impl.plans;


import vkr.planner.model.schedule.RequestProject;
import vkr.planner.model.schedule.Rule;
import vkr.planner.model.schedule.Task;
import vkr.planner.model.tea.PlanTea;
import vkr.planner.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlanTeaBuilder {

    public PlanTea build(Map<String, Object> ruleSet, RequestProject requestProject) {
        PlanTea planTea = new PlanTea();
        List<Rule> ruleList = requestProject.getRuleTypes();
        List<Task> taskList = requestProject.getTaskList();
        for (Task task: taskList){
            if (task.getRuleList().isEmpty()) continue;
            for (Rule rule: task.getRuleList()){
                if (ruleSet.containsKey(rule.getType()) && ruleList.contains(rule)){
                    if (!planTea.getRuleTypes().contains(rule)){
                        planTea.getRuleTypes().add(rule);
                        planTea.getParams().put(rule.getType(), ruleSet.get(rule.getType()));
                    }
                }
            }
        }
        if (planTea.getRuleTypes().isEmpty())
            planTea.setIsEmpty(Boolean.TRUE);
        else planTea.setIsEmpty(Boolean.FALSE);
        return planTea;
    }
}
