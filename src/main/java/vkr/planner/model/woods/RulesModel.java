package vkr.planner.model.woods;

import lombok.Data;
import lombok.NoArgsConstructor;
import vkr.planner.model.RuleType;
import vkr.planner.model.schedule.PlanValidationRule;

import java.util.List;

@Data
@NoArgsConstructor
public class RulesModel {
    private List<RuleType> ruleTypes;
    private List<PlanValidationRule> planValidationRules;
    private List<String> kksToInsulate;
    private List<String> neighboringAreasToCheck;
    private Boolean isValidateKksToInsulate;
    private Double minHeightOfWoodsToCreate;
    private Boolean isEmpty;

    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
