package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.*;
import vkr.planner.service.CheckRuleService;

import java.util.List;

@Component
public class CheckRuleServiceImplKks implements CheckRuleService<PlanWoods, TechnicalDescriptionWoods> {
    public static final RuleType RULE_TYPE = RuleType.KKS;
    @Override
    public PlanWoods checkByRule(PlanWoods planWoods, TechnicalDescriptionWoods technicalDescriptionWoods) {

        List<Pipe> collisionPipeList = technicalDescriptionWoods.getAreaList().stream()
                .flatMap(area -> area.getPipeList().stream())
                .filter(pipe ->
                            planWoods.getKksToInsulate().contains(pipe.getKks()) && !pipe.isNeedToBeThermallyTnsulated()
                        ||  !planWoods.getKksToInsulate().contains(pipe.getKks()) && pipe.isNeedToBeThermallyTnsulated()
                        )
                .toList();
        planWoods.getRuleTypeResult().put(RuleType.KKS, getResult(collisionPipeList));
        collisionPipeList.forEach(pipe -> technicalDescriptionWoods.updatePipe(pipe, fixPipeThermalIsolation(pipe)));
        return planWoods;
    }
    @Override
    public RuleType getRuleType() {
        return RULE_TYPE;
    }

    public String getResult(List<Pipe> collisionPipeList){
        StringBuilder stringBuilder = new StringBuilder("В ходе проверки выявлены коллизии в проходках:\n");
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
