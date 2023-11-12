package vkr.planner.convert;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.Task;
import vkr.planner.model.woods.RuleType;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.WoodsRuleSet;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class RulesModelBuilder {
    public RulesModel build(WoodsRuleSet woodsRuleSet, Plan plan){ // построение сценария проверки задач на основании плана-графика и набора правил
        RulesModel rulesModel = new RulesModel();
        rulesModel.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!

        if (woodsRuleSet.getIsValidateKksToInsulate()) {
            Optional<Task> task = plan.getTaskByType(Task.TaskType.THERMAL_INSULATION);
            if (task.isPresent() && !task.get().isBlocker()){
                rulesModel.setKksToInsulate(woodsRuleSet.getKksToInsulate());
                rulesModel.getRuleTypes().add(RuleType.KKS);
            }
        }
        if (woodsRuleSet.getNeighboringAreasToCheck() != null){
            rulesModel.setNeighboringAreasToCheck(woodsRuleSet.getNeighboringAreasToCheck());
            rulesModel.getRuleTypes().add(RuleType.AREA);
        }
        if (woodsRuleSet.getMinHeightOfWoodsToCreate() != null){
            rulesModel.setMinHeightOfWoodsToCreate(woodsRuleSet.getMinHeightOfWoodsToCreate());
            rulesModel.getRuleTypes().add(RuleType.LEVEL);
        }
        if (rulesModel.getRuleTypes().isEmpty())
            rulesModel.setIsEmpty(Boolean.TRUE);
        else rulesModel.setIsEmpty(Boolean.FALSE);
        return rulesModel;
    }
}
