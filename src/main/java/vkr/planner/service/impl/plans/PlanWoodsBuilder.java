package vkr.planner.service.impl.plans;

import vkr.planner.model.schedule.Project;
import vkr.planner.model.woods.PlanWoods;

import java.util.ArrayList;

public class PlanWoodsBuilder {

    public PlanWoods build(WoodsRuleSet ruleSet, Project project) {
        PlanWoods planWoods = new PlanWoods();
        planWoods.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!

        if (project.getTaskByType(TaskType.ПРОВЕРКА_КОДОВ_ККС).isPresent()) {
            if (!ruleSet.getKksToInsulate().isEmpty()){
                planWoods.setKksToInsulate(ruleSet.getKksToInsulate());
                planWoods.getRuleTypes().add(RuleType.KKS);
            }
        }
        if (project.getTaskByType(TaskType.УСТАНОВКА_ЛЕСОВ).isPresent()){
            if (ruleSet.getMinHeightOfWoodsToCreate() != null){
            planWoods.setMinHeightOfWoodsToCreate(ruleSet.getMinHeightOfWoodsToCreate());
            planWoods.getRuleTypes().add(RuleType.LEVEL);
            }
        }
        if (planWoods.getRuleTypes().isEmpty())
            planWoods.setIsEmpty(Boolean.TRUE);
        else planWoods.setIsEmpty(Boolean.FALSE);
        return planWoods;
    }
}
