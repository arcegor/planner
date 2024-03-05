package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.service.ValidateByRuleService;

@Component
public class ValidateByRuleServiceImplTemp implements ValidateByRuleService {
    public static final String RULE_TYPE = "Температура воды";
    @Override
    public Plan validateByRule(Plan plan) {
        if ((int) plan.getConditionsMap().get(RULE_TYPE) < technicalDescriptionTea.getTemp()){
            plan.getRuleResult().put(RULE_TYPE,
                    "Кипятим воду до 100 градусов");
        }
        else plan.getRuleResult().put(RULE_TYPE,
                "Вода уже вскипячена");
        return plan;
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
