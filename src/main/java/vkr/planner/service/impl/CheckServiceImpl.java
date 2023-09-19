package vkr.planner.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
import vkr.planner.model.Task;
import vkr.planner.service.CheckService;

import java.util.Comparator;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    @Override
    public boolean checkPlanByRule(Plan plan, @NotNull Rule rule) {
        return switch (rule.getRuleType()) {
            case ORDER -> checkOrder(plan);
            case DURATION -> checkDuration(plan);
            case COSTS -> checkCosts(plan);
        };
    }
    public boolean checkOrder(@NotNull Plan plan){
        List<Task> taskList = plan.getTaskList();
        return taskList.stream().sorted(Comparator
                .comparing(Task::getOrder)).toList().equals(taskList);
    }
    public boolean checkDuration(@NotNull Plan plan){
        return plan.getTaskList().stream().map(a -> a.getDuration().toDays())
                .reduce(0L, Long::sum) <= plan.getMaxDuration().toDays();
    }
    public boolean checkCosts(@NotNull Plan plan){
        return plan.getTaskList().stream().map(Task::getCosts)
                .reduce(0, Integer::sum) <= plan.getMaxCosts();
    }
}
