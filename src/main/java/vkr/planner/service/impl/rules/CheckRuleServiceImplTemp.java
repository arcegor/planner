package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckScenarioTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplTemp implements CheckRuleService<CheckScenarioTea, TechnicalDescriptionTea> {
    public static final RuleType RULE_TYPE = RuleType.ТЕМПЕРАТУРА_ВОДЫ;
    @Override
    public TechnicalDescriptionTea checkByRule(CheckScenarioTea checkScenarioTea, TechnicalDescriptionTea technicalDescriptionTea) {
        if (checkScenarioTea.getTemp() < technicalDescriptionTea.getTemp()){
            technicalDescriptionTea.getRuleTypeMapResult().put(RuleType.ТЕМПЕРАТУРА_ВОДЫ,
                    "Кипятим воду до 100 градусов");
        }
        return technicalDescriptionTea;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
