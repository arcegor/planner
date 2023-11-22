package vkr.planner.service.impl.plans;

import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.ProjectType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.schedule.TaskType;
import vkr.planner.model.woods.CheckPlanWoods;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckPlanBuilder;

import java.util.ArrayList;

public class PlanWoodsBuilder implements CheckPlanBuilder<CheckPlanWoods, WoodsRuleSet> {
    @Override
    public ProjectType getPlanType() {
        return ProjectType.ГЕРМЕТИЗАЦИЯ_ТРУБЫ;
    }
    @Override
    public CheckPlanWoods build(WoodsRuleSet ruleSet, Project project) {
        CheckPlanWoods checkPlanWoods = new CheckPlanWoods();
        checkPlanWoods.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!

        if (project.getTaskByType(TaskType.VALIDATE_KKS).isPresent()) {

            if (!ruleSet.getKksToInsulate().isEmpty()){
                checkPlanWoods.setKksToInsulate(ruleSet.getKksToInsulate());
                checkPlanWoods.getRuleTypes().add(RuleType.KKS);
            }
        }
        if (project.getTaskByType(TaskType.CREATION_WOODS).isPresent()){
            if (checkPlanWoods.getMinHeightOfWoodsToCreate() == null)
                checkPlanWoods.setMinHeightOfWoodsToCreate(1.5);
            else checkPlanWoods.setMinHeightOfWoodsToCreate(ruleSet.getMinHeightOfWoodsToCreate());
            checkPlanWoods.getRuleTypes().add(RuleType.LEVEL);
        }
        if (checkPlanWoods.getRuleTypes().isEmpty())
            checkPlanWoods.setIsEmpty(Boolean.TRUE);
        else checkPlanWoods.setIsEmpty(Boolean.FALSE);
        return checkPlanWoods;
    }
}
