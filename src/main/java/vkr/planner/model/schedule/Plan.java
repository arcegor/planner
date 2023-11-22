package vkr.planner.model.schedule;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Plan {
    private List<RuleType> ruleTypes;
    private List<Task> taskList;
    protected Map<RuleType, String> ruleTypeResult = new HashMap<>();
    private boolean isEmpty;
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
