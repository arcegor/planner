package vkr.planner.convert;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.TaskType;
import vkr.planner.model.RuleType;
import vkr.planner.model.tea.RulesModelTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.WoodsRuleSet;

import java.util.ArrayList;

@Component
public class RulesModelBuilder {
    public RulesModel build(WoodsRuleSet woodsRuleSet, Plan plan){ // построение сценария проверки задач на основании плана-графика и набора правил
        RulesModel rulesModel = new RulesModel();
        rulesModel.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!

        if (plan.getTaskByType(TaskType.VALIDATE_KKS).isPresent()) {
            boolean isNeedToBeDone = !plan.getTaskByType(TaskType.VALIDATE_KKS).get().isDone();
            if (!woodsRuleSet.getKksToInsulate().isEmpty() && isNeedToBeDone){
                rulesModel.setKksToInsulate(woodsRuleSet.getKksToInsulate());
                rulesModel.getRuleTypes().add(RuleType.KKS);
            }
        }
        if (plan.getTaskByType(TaskType.CREATION_WOODS).isPresent()){
            boolean isNeedToBeDone = !plan.getTaskByType(TaskType.CREATION_WOODS).get().isDone();
            if (isNeedToBeDone){
                if (rulesModel.getMinHeightOfWoodsToCreate() == null)
                    rulesModel.setMinHeightOfWoodsToCreate(1.5);
                else rulesModel.setMinHeightOfWoodsToCreate(woodsRuleSet.getMinHeightOfWoodsToCreate());
            }
            rulesModel.getRuleTypes().add(RuleType.LEVEL);
        }
        if (rulesModel.getRuleTypes().isEmpty())
            rulesModel.setIsEmpty(Boolean.TRUE);
        else rulesModel.setIsEmpty(Boolean.FALSE);



        return rulesModel;
    }
    public RulesModelTea build(TeaRuleSet teaRuleSet, Plan plan){ // построение сценария проверки задач на основании плана-графика и набора правил
        RulesModelTea rulesModel = new RulesModelTea();
        rulesModel.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!


        if (plan.getTaskByType(TaskType.ВСКИПЯТИТЬ_ВОДУ).isPresent()){
            int temp = teaRuleSet.getTemp();
            rulesModel.setTemp(temp);
            rulesModel.getRuleTypes().add(RuleType.ТЕМПЕРАТУРА_ВОДЫ);
        }
        if (plan.getTaskByType(TaskType.ПОЛОЖИТЬ_МЯТУ).isPresent()){
            boolean mint = teaRuleSet.getIsMint();
            rulesModel.setMint(mint);
            rulesModel.getRuleTypes().add(RuleType.НАЛИЧИЕ_МЯТЫ);
        }
        if (rulesModel.getRuleTypes().isEmpty())
            rulesModel.setIsEmpty(Boolean.TRUE);
        else rulesModel.setIsEmpty(Boolean.FALSE);



        return rulesModel;
    }
}
