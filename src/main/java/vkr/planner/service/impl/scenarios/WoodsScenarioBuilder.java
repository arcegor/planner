package vkr.planner.service.impl.scenarios;

import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.schedule.TaskType;
import vkr.planner.model.woods.CheckScenarioWoods;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckScenarioBuilder;

import java.util.ArrayList;

public class WoodsScenarioBuilder implements CheckScenarioBuilder<CheckScenarioWoods, WoodsRuleSet> {
    @Override
    public PlanType getPlanType() {
        return PlanType.СТРОЙКА_РЕАКТОР;
    }
    @Override
    public CheckScenarioWoods build(WoodsRuleSet ruleSet, Plan plan) {
        CheckScenarioWoods checkScenarioWoods = new CheckScenarioWoods();
        checkScenarioWoods.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!

        if (plan.getTaskByType(TaskType.VALIDATE_KKS).isPresent()) {

            if (!ruleSet.getKksToInsulate().isEmpty()){
                checkScenarioWoods.setKksToInsulate(ruleSet.getKksToInsulate());
                checkScenarioWoods.getRuleTypes().add(RuleType.KKS);
            }
        }
        if (plan.getTaskByType(TaskType.CREATION_WOODS).isPresent()){
            if (checkScenarioWoods.getMinHeightOfWoodsToCreate() == null)
                checkScenarioWoods.setMinHeightOfWoodsToCreate(1.5);
            else checkScenarioWoods.setMinHeightOfWoodsToCreate(ruleSet.getMinHeightOfWoodsToCreate());
            checkScenarioWoods.getRuleTypes().add(RuleType.LEVEL);
        }
        if (checkScenarioWoods.getRuleTypes().isEmpty())
            checkScenarioWoods.setIsEmpty(Boolean.TRUE);
        else checkScenarioWoods.setIsEmpty(Boolean.FALSE);
        return checkScenarioWoods;
    }
}
