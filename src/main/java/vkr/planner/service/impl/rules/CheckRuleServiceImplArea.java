package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.woods.*;
import vkr.planner.service.CheckRuleService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CheckRuleServiceImplArea implements CheckRuleService<CheckPlanWoods, TechnicalDescriptionWoods> {
    public static final RuleType RULE_TYPE = RuleType.AREA;
    @Override
    public TechnicalDescriptionWoods checkByRule(CheckPlanWoods checkPlanWoods, TechnicalDescriptionWoods technicalDescriptionWoods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Area area: technicalDescriptionWoods.getAreaList()){
            for (Pipe pipe : area.getPipeList()){
                Set<String> result = pipe.getNeighbouringAreas().stream()
                        .distinct()
                        .filter(checkPlanWoods.getNeighboringAreasToCheck()::contains)
                        .collect(Collectors.toSet());
                if (result.isEmpty())
                    continue;
                stringBuilder.append(
                        String.format("Проходка %s совпадает по смежным помещениям : %s \n",
                                pipe, String.join(", ", checkPlanWoods.getNeighboringAreasToCheck()))
                );
            }
        }
        technicalDescriptionWoods.getRuleTypeResult().put(RuleType.AREA, stringBuilder.toString());
        return technicalDescriptionWoods;
    }

    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }
}
