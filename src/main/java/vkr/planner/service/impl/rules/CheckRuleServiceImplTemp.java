package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckPlanTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplTemp implements CheckRuleService<CheckPlanTea, TechnicalDescriptionTea> {
    public static final RuleType RULE_TYPE = RuleType.ТЕМПЕРАТУРА_ВОДЫ;
    @Override
    public TechnicalDescriptionTea checkByRule(CheckPlanTea checkPlanTea, TechnicalDescriptionTea technicalDescriptionTea) {
        if (checkPlanTea.getTemp() < technicalDescriptionTea.getTemp()){
            technicalDescriptionTea.getRuleTypeResult().put(RuleType.ТЕМПЕРАТУРА_ВОДЫ,
                    "Кипятим воду до 100 градусов");
        }
        return technicalDescriptionTea;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
