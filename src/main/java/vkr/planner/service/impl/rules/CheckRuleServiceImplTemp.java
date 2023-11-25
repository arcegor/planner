package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.tea.PlanTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplTemp implements CheckRuleService<PlanTea, TechnicalDescriptionTea> {
    public static final String RULE_TYPE =ТЕМПЕРАТУРА_ВОДЫ;
    @Override
    public PlanTea checkByRule(PlanTea planTea, TechnicalDescriptionTea technicalDescriptionTea) {
        if (planTea.getTemp() < technicalDescriptionTea.getTemp()){
            planTea.getRuleTypeResult().put(RuleType.ТЕМПЕРАТУРА_ВОДЫ,
                    "Кипятим воду до 100 градусов");
        }
        else planTea.getRuleTypeResult().put(RuleType.ТЕМПЕРАТУРА_ВОДЫ,
                "Вода уже вскипячена");
        return planTea;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
