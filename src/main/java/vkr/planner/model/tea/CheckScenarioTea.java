package vkr.planner.model.tea;

import lombok.Data;
import lombok.NoArgsConstructor;
import vkr.planner.model.schedule.RuleType;

import java.util.List;
@Data
@NoArgsConstructor
public class CheckScenarioTea {
    private List<RuleType> ruleTypes;
    private int temp;
    private boolean isMint;
    private boolean isEmpty;

    public void setIsEmpty(Boolean aTrue) {
        this.isEmpty = aTrue;
    }
}
