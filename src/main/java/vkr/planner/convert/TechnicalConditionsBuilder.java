package vkr.planner.convert;

import org.springframework.stereotype.Component;
import vkr.planner.model.db.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TechnicalConditionsBuilder {
    public List<Condition> convertMapToTechnicalConditions(Map<String, String> conditions) {
        List<Condition> conditionList = new ArrayList<>();
        for (String key: conditions.keySet()){
            conditionList.add(
                    new Condition(key.trim(), conditions.get(key).trim())
            );
        }
        return conditionList;
    }
}
