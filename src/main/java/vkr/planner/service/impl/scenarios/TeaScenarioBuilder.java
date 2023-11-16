package vkr.planner.service.impl.scenarios;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanType;
import vkr.planner.model.schedule.TaskType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckScenarioTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.service.CheckScenarioBuilder;

import java.util.ArrayList;

@Component
public class TeaScenarioBuilder implements CheckScenarioBuilder<CheckScenarioTea, TeaRuleSet> {
    @Override
    public PlanType getPlanType() {
        return PlanType.ЗАВАРИВАНИЕ_ЧАЯ;
    }
    @Override
    public CheckScenarioTea build(TeaRuleSet ruleSet, Plan plan) {
        CheckScenarioTea rulesModel = new CheckScenarioTea();
        rulesModel.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!


        if (plan.getTaskByType(TaskType.ВСКИПЯТИТЬ_ВОДУ).isPresent()){
            int temp = ruleSet.getTemp();
            rulesModel.setTemp(temp);
            rulesModel.getRuleTypes().add(RuleType.ТЕМПЕРАТУРА_ВОДЫ);
        }
        if (plan.getTaskByType(TaskType.ПОЛОЖИТЬ_МЯТУ).isPresent()){
            boolean mint = ruleSet.getIsMint();
            rulesModel.setMint(mint);
            rulesModel.getRuleTypes().add(RuleType.НАЛИЧИЕ_МЯТЫ);
        }
        if (rulesModel.getRuleTypes().isEmpty())
            rulesModel.setIsEmpty(Boolean.TRUE);
        else rulesModel.setIsEmpty(Boolean.FALSE);
        return rulesModel;
    }
}
