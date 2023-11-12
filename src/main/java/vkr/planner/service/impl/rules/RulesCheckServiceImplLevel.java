package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.*;
import vkr.planner.service.RulesCheckService;

import java.util.*;

@Component
public class RulesCheckServiceImplLevel implements RulesCheckService<RulesModel, TechnicalDescriptionWoods> {
    public static final RuleType RULE_TYPE = RuleType.LEVEL;
    @Override
    public TechnicalDescriptionWoods checkByRule(RulesModel rulesModel, TechnicalDescriptionWoods technicalDescriptionWoods) {
        Map<Pipe, Double> woodsLevels =new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Проходки, превыщающие высоту ").append(rulesModel.getMinHeightOfWoodsToCreate()).append(" :\n");
        double level = rulesModel.getMinHeightOfWoodsToCreate();
        technicalDescriptionWoods.getAreaList().stream()
                .flatMap(area -> area.getPipeList().stream())
                .filter(pipe -> Math.abs(pipe.getZ() - pipe.getLevel()) > level)
                .forEach(pipe ->
                        woodsLevels.put(pipe, round(Math.abs((pipe.getZ() - pipe.getLevel())), 3))
                );
        technicalDescriptionWoods.getRuleTypeResult().put(RuleType.LEVEL, getResult(woodsLevels, stringBuilder));
        return technicalDescriptionWoods;
    }
    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }

    public String getResult(Map<Pipe, Double> woodsLevels, StringBuilder stringBuilder){
        for (Pipe pipe: woodsLevels.keySet()){
            stringBuilder.append(pipe).append(",\n");
        }
        return stringBuilder.toString();
    }
}
