package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.desk.TechnicalDescriptionDesk;
import vkr.planner.model.schedule.Plan;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRulesServiceImplDesk implements CheckRuleService<TechnicalDescriptionDesk> {
    public static final String RULE_TYPE = "Температура утюга";
    @Override
    public Plan checkByRule(Plan plan, TechnicalDescriptionDesk technicalDescriptionDesk) {
        if (technicalDescriptionDesk.getTemp() > (int)plan.getParams().get(RULE_TYPE)){
            plan.getRuleResult().put(RULE_TYPE,
                    "Нагреваем утюг до 110 градусов");
        }
        else{
            plan.getRuleResult().put(RULE_TYPE,
                    "Утюг уже достаточно нагрет");
        }
        return plan;
    }

    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
