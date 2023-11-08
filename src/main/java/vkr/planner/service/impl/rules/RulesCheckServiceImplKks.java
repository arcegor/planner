package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.Pipe;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.RulesCheckService;

import java.util.List;

@Component
public class RulesCheckServiceImplKks implements RulesCheckService<TechnicalDescriptionWoods> {
    public static final String RULE_TYPE = "Коды ККС";
    public String checkByRule(WoodsRuleSet woodsRuleSet, TechnicalDescriptionWoods technicalDescriptionWoods) {
        StringBuilder stringBuilder = new StringBuilder("Коллизии в проходках:\n");
        List<Pipe> collisionPipeList = technicalDescriptionWoods.getAreaList().stream()
                .flatMap(area -> area.getPipeList().stream())
                .filter(pipe ->
                            woodsRuleSet.getKksToInsulate().contains(pipe.getKks()) && !pipe.isNeedToBeThermallyTnsulated()
                        ||  !woodsRuleSet.getKksToInsulate().contains(pipe.getKks()) && pipe.isNeedToBeThermallyTnsulated()
                        )
                .toList();
        for (Pipe pipe: collisionPipeList){
            stringBuilder.append(pipe).append(",\n");
        }
        return stringBuilder.toString();
    }
    @Override
    public String getRuleType() {
        return RULE_TYPE;
    }
}
