package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.Area;
import vkr.planner.model.woods.Pipe;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.RulesCheckService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RulesCheckServiceImplArea implements RulesCheckService<TechnicalDescriptionWoods> {
    public static final String RULE_TYPE = "Смежные помещения";
    @Override
    public String checkByRule(WoodsRuleSet woodsRuleSet, TechnicalDescriptionWoods technicalDescriptionWoods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Area area: technicalDescriptionWoods.getAreaList()){
            for (Pipe pipe : area.getPipeList()){
                Set<String> result = pipe.getNeighbouringAreas().stream()
                        .distinct()
                        .filter(woodsRuleSet.getNeighboringAreasToCheck()::contains)
                        .collect(Collectors.toSet());
                if (result.isEmpty())
                    continue;
                stringBuilder.append(
                        String.format("Проходка %s совпадает по смежным помещениям : %s \n",
                                pipe, String.join(", ", woodsRuleSet.getNeighboringAreasToCheck()))
                );
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
