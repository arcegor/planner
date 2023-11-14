package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vkr.planner.model.RuleType;
import vkr.planner.model.tea.RulesModelTea;
import vkr.planner.model.tea.Teapot;
import vkr.planner.service.RulesCheckService;

@Component
public class RulesCheckServiceImplMint implements RulesCheckService<RulesModelTea, Teapot> {
    public static final RuleType RULE_TYPE = RuleType.НАЛИЧИЕ_МЯТЫ;
    @Override
    public Teapot checkByRule(RulesModelTea rulesModelTea, Teapot teapot) {
        if (!rulesModelTea.isMint()){
            teapot.getRuleTypeMapResult().put(RuleType.НАЛИЧИЕ_МЯТЫ,
                    "Кладем мяту");
        }
        return teapot;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
