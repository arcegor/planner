package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplTemp implements CheckRuleService<TechnicalDescriptionTea> {
    public static final String RULE_TYPE = "Температура воды";
    @Override
    public Plan checkByRule(Plan plan, TechnicalDescriptionTea technicalDescriptionTea) {
        if ((int) plan.getParams().get(RULE_TYPE) < technicalDescriptionTea.getTemp()){
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
