package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckPlanTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckRuleService;

@Component
public class CheckRuleServiceImplMint implements CheckRuleService<CheckPlanTea, TechnicalDescriptionTea> {
    public static final RuleType RULE_TYPE = RuleType.НАЛИЧИЕ_МЯТЫ;
    @Override
    public TechnicalDescriptionTea checkByRule(CheckPlanTea checkPlanTea, TechnicalDescriptionTea technicalDescriptionTea) {
        if (!checkPlanTea.isMint()){
            technicalDescriptionTea.getRuleTypeResult().put(RuleType.НАЛИЧИЕ_МЯТЫ,
                    "Кладем мяту");
        }
        return technicalDescriptionTea;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
