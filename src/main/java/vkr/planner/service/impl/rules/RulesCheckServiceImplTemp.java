package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.RuleType;
import vkr.planner.model.tea.RulesModelTea;
import vkr.planner.model.tea.Teapot;
import vkr.planner.service.RulesCheckService;

@Component
public class RulesCheckServiceImplTemp implements RulesCheckService<RulesModelTea, Teapot> {
    public static final RuleType RULE_TYPE = RuleType.ТЕМПЕРАТУРА_ВОДЫ;
    @Override
    public Teapot checkByRule(RulesModelTea rulesModelTea, Teapot teapot) {
        if (rulesModelTea.getTemp() < teapot.getTemp()){
            teapot.getRuleTypeMapResult().put(RuleType.ТЕМПЕРАТУРА_ВОДЫ,
                    "Кипятим воду до 100 градусов");
        }
        return teapot;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
