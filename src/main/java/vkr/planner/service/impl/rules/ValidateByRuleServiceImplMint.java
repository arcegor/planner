package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.ValidateByRuleService;

@Component
public class ValidateByRuleServiceImplMint implements ValidateByRuleService {
    public static final String RULE_TYPE = "Наличие мяты";
    @Override
    public Plan validateByRule(Plan plan) {
        String flag = (String) plan.getConditionsMap().get(RULE_TYPE);
        if (flag.equalsIgnoreCase("Да")){
            plan.getRuleResult().put(RULE_TYPE,
                    "Кладем мяту");
        }
        else plan.getRuleResult().put(RULE_TYPE, "Нужно купить мяту");
        return plan;
    }

    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
