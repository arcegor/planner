package vkr.planner.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanValidationRule {

    private PlanValidationRuleType planValidationRuleType;
    public enum PlanValidationRuleType {
        ORDER,
        COSTS,
        DURATION,
        ALL_TASKS,
        NOT_DONE_TASKS,
    }
}

