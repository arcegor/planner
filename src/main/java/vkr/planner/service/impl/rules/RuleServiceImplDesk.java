package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.Plan;
import vkr.planner.model.db.Condition;
import vkr.planner.service.RuleService;

@Component
public class RuleServiceImplDesk implements RuleService {
    public static final String RULE_TYPE = "Температура утюга";
    @Override
    public void applyRule(Plan plan, Condition condition) {
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
