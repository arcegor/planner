package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.Plan;
import vkr.planner.service.ValidateByRuleService;

@Component
public class ValidateByRuleServiceImplDesk implements ValidateByRuleService {
    public static final String RULE_TYPE = "Температура утюга";
    @Override
    public Plan validateByRule(Plan plan) {
        return plan;
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
