package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.*;
import vkr.planner.service.RulesCheckService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RulesCheckServiceImplArea implements RulesCheckService<RulesModel, TechnicalDescriptionWoods> {
    public static final RuleType RULE_TYPE = RuleType.AREA;
    @Override
    public TechnicalDescriptionWoods checkByRule(RulesModel rulesModel, TechnicalDescriptionWoods technicalDescriptionWoods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Area area: technicalDescriptionWoods.getAreaList()){
            for (Pipe pipe : area.getPipeList()){
                Set<String> result = pipe.getNeighbouringAreas().stream()
                        .distinct()
                        .filter(rulesModel.getNeighboringAreasToCheck()::contains)
                        .collect(Collectors.toSet());
                if (result.isEmpty())
                    continue;
                stringBuilder.append(
                        String.format("Проходка %s совпадает по смежным помещениям : %s \n",
                                pipe, String.join(", ", rulesModel.getNeighboringAreasToCheck()))
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
