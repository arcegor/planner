package vkr.planner.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
import vkr.planner.model.Task;
import vkr.planner.service.CheckService;

import java.util.Comparator;
import java.util.List;

@Component
public class CheckServiceImpl implements CheckService {

    public static final String REQUEST_TYPE = "Первый";

    public static final String CHECK_RESPONSE = "Проверка запроса не пройдена!";

    @Override
    public boolean checkPlanByRule(Plan plan, @NotNull Rule rule) {
        return switch (rule.getRuleType()) {
            case ORDER -> checkOrder(plan);
            case DURATION -> checkDuration(plan);
            case COSTS -> checkCosts(plan);
        };
    }
    @Override
    public String check(CheckRequest checkRequest) {
        return CHECK_RESPONSE;
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
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
