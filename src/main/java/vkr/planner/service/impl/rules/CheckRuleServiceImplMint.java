package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.tea.PlanTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplMint implements CheckRuleService<PlanTea, TechnicalDescriptionTea> {
    public static final RuleType RULE_TYPE = RuleType.НАЛИЧИЕ_МЯТЫ;
    @Override
    public PlanTea checkByRule(PlanTea planTea, TechnicalDescriptionTea technicalDescriptionTea) {
        if (!planTea.isMint()){
            planTea.getRuleTypeResult().put(RuleType.НАЛИЧИЕ_МЯТЫ,
                    "Кладем мяту");
        }
        else planTea.getRuleTypeResult().put(RuleType.НАЛИЧИЕ_МЯТЫ, "Мята уже положена");
        return planTea;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
