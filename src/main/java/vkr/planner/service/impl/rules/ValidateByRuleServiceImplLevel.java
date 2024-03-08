//package vkr.planner.service.impl.rules;
//
//import org.springframework.stereotype.Component;
//import vkr.planner.model.Plan;
//import vkr.planner.model.woods.*;
//import vkr.planner.service.ValidateByRuleService;
//
//import java.util.*;
//
//@Component
//public class ValidateByRuleServiceImplLevel implements ValidateByRuleService {
//    public static final String RULE_TYPE = "Уровень установки лесов";
//    @Override
//    public Plan applyRule(Plan plan) {
//        Map<Pipe, Double> woodsLevels =new HashMap<>();
//        StringBuilder stringBuilder = new StringBuilder();
//        double level = Double.parseDouble((String) plan.getConditionsMap().get(RULE_TYPE));
//        stringBuilder.append("Проходки, превыщающие высоту ").append(level).append(" :\n");
//
//        technicalDescriptionWoods.getAreaList().stream()
//                .flatMap(area -> area.getPipeList().stream())
//                .filter(pipe -> Math.abs(pipe.getZ() - pipe.getLevel()) > level)
//                .forEach(pipe ->
//                        woodsLevels.put(pipe, round(Math.abs((pipe.getZ() - pipe.getLevel())), 3))
//                );
//        plan.getRuleResult().put(RULE_TYPE, getResult(woodsLevels, stringBuilder));
//        return plan;
//    }
//    public static double round(double value, int places) {
//        double scale = Math.pow(10, places);
//        return Math.round(value * scale) / scale;
//    }
//    @Override
//    public String getRuleType() {
//        return RULE_TYPE;
//    }
//
//    public String getResult(Map<Pipe, Double> woodsLevels, StringBuilder stringBuilder){
//        for (Pipe pipe: woodsLevels.keySet()){
//            stringBuilder.append(pipe).append(",\n");
//        }
//        return stringBuilder.toString();
//    }
//}
