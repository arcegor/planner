//package vkr.planner.service.impl.rules;
//
//import org.springframework.stereotype.Component;
//import vkr.planner.model.Plan;
//import vkr.planner.model.woods.*;
//import vkr.planner.service.ValidateByRuleService;
//
//import java.util.List;
//
//@Component
//public class ValidateByRuleServiceImplKks implements ValidateByRuleService {
//    public static final String RULE_TYPE = "Коллизии в кодах ККС";
//    @Override
//    public Plan applyRule(Plan plan) {
//        List<String> kksToInsulate = (List<String>) plan.getConditionsMap().get(RULE_TYPE);
//        List<Pipe> collisionPipeList = technicalDescriptionWoods.getAreaList().stream()
//                .flatMap(area -> area.getPipeList().stream())
//                .filter(pipe ->
//                                kksToInsulate.contains(pipe.getKks()) && !pipe.isNeedToBeThermallyTnsulated()
//                        ||  !kksToInsulate.contains(pipe.getKks()) && pipe.isNeedToBeThermallyTnsulated()
//                        )
//                .toList();
//        plan.getRuleResult().put(RULE_TYPE, getResult(collisionPipeList));
//        collisionPipeList.forEach(pipe -> technicalDescriptionWoods.updatePipe(pipe, fixPipeThermalIsolation(pipe)));
//        return plan;
//    }
//    @Override
//    public String getRuleType() {
//        return RULE_TYPE;
//    }
//
//    public String getResult(List<Pipe> collisionPipeList){
//        StringBuilder stringBuilder = new StringBuilder("В ходе проверки выявлены коллизии в проходках:\n");
//        for (Pipe pipe: collisionPipeList){
//            stringBuilder.append(pipe).append(",\n");
//        }
//        stringBuilder.append("Всего коллизий: ").append(collisionPipeList.size());
//        return stringBuilder.toString();
//    }
//    public Pipe fixPipeThermalIsolation(Pipe pipe){
//        pipe.setNeedToBeThermallyTnsulated(!pipe.isNeedToBeThermallyTnsulated());
//        return pipe;
//    }
//}
