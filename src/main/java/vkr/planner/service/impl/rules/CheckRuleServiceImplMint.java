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
    public CheckPlanTea checkByRule(CheckPlanTea checkPlanTea, TechnicalDescriptionTea technicalDescriptionTea) {
        if (!checkPlanTea.isMint()){
            checkPlanTea.getRuleTypeResult().put(RuleType.НАЛИЧИЕ_МЯТЫ,
                    "Кладем мяту");
        }
        else checkPlanTea.getRuleTypeResult().put(RuleType.НАЛИЧИЕ_МЯТЫ, "Мята уже положена");
        return checkPlanTea;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
