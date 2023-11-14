package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.RuleType;
import vkr.planner.model.woods.*;
import vkr.planner.service.RulesCheckService;

import java.util.List;

@Component
public class RulesCheckServiceImplKks implements RulesCheckService<RulesModel, TechnicalDescriptionWoods> {
    public static final RuleType RULE_TYPE = RuleType.KKS;
    @Override
    public TechnicalDescriptionWoods checkByRule(RulesModel rulesModel, TechnicalDescriptionWoods technicalDescriptionWoods) {

        List<Pipe> collisionPipeList = technicalDescriptionWoods.getAreaList().stream()
                .flatMap(area -> area.getPipeList().stream())
                .filter(pipe ->
                            rulesModel.getKksToInsulate().contains(pipe.getKks()) && !pipe.isNeedToBeThermallyTnsulated()
                        ||  !rulesModel.getKksToInsulate().contains(pipe.getKks()) && pipe.isNeedToBeThermallyTnsulated()
                        )
                .toList();
        technicalDescriptionWoods.getRuleTypeResult().put(RuleType.KKS, getResult(collisionPipeList));
        collisionPipeList.forEach(pipe -> technicalDescriptionWoods.updatePipe(pipe, fixPipeThermalIsolation(pipe)));

        return technicalDescriptionWoods;
    }
    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }

    public String getResult(List<Pipe> collisionPipeList){
        StringBuilder stringBuilder = new StringBuilder("Коллизии в проходках:\n");
        for (Pipe pipe: collisionPipeList){
            stringBuilder.append(pipe).append(",\n");
        }
        stringBuilder.append("Всего коллизий: ").append(collisionPipeList.size());
        return stringBuilder.toString();
    }
    public Pipe fixPipeThermalIsolation(Pipe pipe){
        pipe.setNeedToBeThermallyTnsulated(!pipe.isNeedToBeThermallyTnsulated());
        return pipe;
    }
}
