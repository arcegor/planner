package vkr.planner.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanValidationRule;
import vkr.planner.model.woods.RuleType;
import vkr.planner.model.woods.WoodsRuleSet;
@Service
public interface RulesCheckService<R, M> {
    M checkByRule(R r, M m);
    RuleType getRuleType();
    default boolean isPlanValidByRule(Plan plan, @NotNull PlanValidationRule planValidationRule){
        return true;
    };
}
