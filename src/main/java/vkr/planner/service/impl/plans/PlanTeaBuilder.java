package vkr.planner.service.impl.plans;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.ProjectType;
import vkr.planner.model.schedule.TaskType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckPlanTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.service.CheckPlanBuilder;

import java.util.ArrayList;

@Component
public class PlanTeaBuilder implements CheckPlanBuilder<CheckPlanTea, TeaRuleSet> {
    @Override
    public ProjectType getPlanType() {
        return ProjectType.ЗАВАРИВАНИЕ_ЧАЯ;
    }
    @Override
    public CheckPlanTea build(TeaRuleSet ruleSet, Project project) {
        CheckPlanTea checkPlanTea = new CheckPlanTea();
        checkPlanTea.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!


        if (project.getTaskByType(TaskType.ВСКИПЯТИТЬ_ВОДУ).isPresent()){
            int temp = ruleSet.getTemp();
            checkPlanTea.setTemp(temp);
            checkPlanTea.getRuleTypes().add(RuleType.ТЕМПЕРАТУРА_ВОДЫ);
        }
        if (project.getTaskByType(TaskType.ПОЛОЖИТЬ_МЯТУ).isPresent()){
            boolean mint = ruleSet.getIsMint();
            checkPlanTea.setMint(mint);
            checkPlanTea.getRuleTypes().add(RuleType.НАЛИЧИЕ_МЯТЫ);
        }
        if (checkPlanTea.getRuleTypes().isEmpty())
            checkPlanTea.setIsEmpty(Boolean.TRUE);
        else checkPlanTea.setIsEmpty(Boolean.FALSE);
        return checkPlanTea;
    }
}
