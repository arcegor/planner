package vkr.planner.model.schedule;

import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
public class Plan {
    private List<RuleType> ruleTypes;
    private boolean isEmpty;
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
