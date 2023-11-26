package vkr.planner.service.impl.rules;

import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.woods.*;
import vkr.planner.service.CheckRuleService;

import java.util.Collection;
import java.util.List;

@Component
public class CheckRuleServiceImplKks implements CheckRuleService<TechnicalDescriptionWoods> {
    public static final String RULE_TYPE = "Коллизии в кодах ККС";
    @Override
    public Plan checkByRule(Plan plan, TechnicalDescriptionWoods technicalDescriptionWoods) {
        List<String> kksToInsulate = (List<String>) plan.getParams().get(RULE_TYPE);
        List<Pipe> collisionPipeList = technicalDescriptionWoods.getAreaList().stream()
                .flatMap(area -> area.getPipeList().stream())
                .filter(pipe ->
                                kksToInsulate.contains(pipe.getKks()) && !pipe.isNeedToBeThermallyTnsulated()
                        ||  !kksToInsulate.contains(pipe.getKks()) && pipe.isNeedToBeThermallyTnsulated()
                        )
                .toList();
        plan.getRuleResult().put(RULE_TYPE, getResult(collisionPipeList));
        collisionPipeList.forEach(pipe -> technicalDescriptionWoods.updatePipe(pipe, fixPipeThermalIsolation(pipe)));
        return plan;
    }
    @Override
    public String getRuleType() {
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
