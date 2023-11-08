package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.Pipe;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.RulesCheckService;

import java.util.List;

@Component
public class RulesCheckServiceImplLevel implements RulesCheckService<TechnicalDescriptionWoods> {
    public static final String RULE_TYPE = "Высота лесов";
    public String checkByRule(WoodsRuleSet woodsRuleSet, TechnicalDescriptionWoods technicalDescriptionWoods) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Леса, превыщающие высоту ").append(woodsRuleSet.getWoodsTopBound()).append(" :\n");
        double level = woodsRuleSet.getWoodsTopBound();
        technicalDescriptionWoods.getAreaList().stream()
                .flatMap(area -> area.getPipeList().stream())
                .filter(pipe -> Math.abs(pipe.getZ() - pipe.getLevel()) > level)
                .forEach(pipe ->
                        stringBuilder.append(pipe).append(", ").append(Math.round(Math.abs((pipe.getZ() - pipe.getLevel()) - level)))
                                .append(",\n")
                );
        return stringBuilder.toString();
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
