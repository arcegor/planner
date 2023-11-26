package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplMint implements CheckRuleService<TechnicalDescriptionTea> {
    public static final String RULE_TYPE = "Наличие мяты";
    @Override
    public Plan checkByRule(Plan plan, TechnicalDescriptionTea technicalDescriptionTea) {
        if (plan.getParams().get(RULE_TYPE).equals("Да")){
            plan.getRuleResult().put(RULE_TYPE,
                    "Кладем мяту");
        }
        else plan.getRuleResult().put(RULE_TYPE, "Мята уже положена");
        return plan;
    }

    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
