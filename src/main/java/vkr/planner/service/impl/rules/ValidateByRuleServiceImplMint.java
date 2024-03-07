package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.Plan;
import vkr.planner.service.ValidateByRuleService;

@Component
public class ValidateByRuleServiceImplMint implements ValidateByRuleService {
    public static final String RULE_TYPE = "Наличие мяты";
    @Override
    public Plan validateByRule(Plan plan) {
        return plan;
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
