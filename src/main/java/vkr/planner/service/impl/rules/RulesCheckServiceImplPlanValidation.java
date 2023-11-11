package vkr.planner.service.impl.rules;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanValidationRule;
import vkr.planner.model.schedule.Task;
import vkr.planner.model.woods.RuleType;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckService;
import vkr.planner.service.RulesCheckService;

import java.util.Comparator;
import java.util.List;

@Component
public class RulesCheckServiceImplPlanValidation implements RulesCheckService<RulesModel, Plan> {

    public static final RuleType RULE_TYPE = RuleType.PLAN_VALIDATION;

    public static final String CHECK_RESPONSE = "План не валиден!";

    @Override
    public Plan checkByRule(RulesModel woodsRuleSet, Plan plan) {
        return plan;
    }
    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
    @Override
    public boolean isPlanValidByRule(Plan plan, @NotNull PlanValidationRule planValidationRule) {
        return switch (planValidationRule.getPlanValidationRuleType()) {
            case ORDER -> checkOrder(plan);
            case DURATION -> checkDuration(plan);
            case COSTS -> checkCosts(plan);
            case ALL_TASKS -> false;
            case NOT_DONE_TASKS -> false;
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
