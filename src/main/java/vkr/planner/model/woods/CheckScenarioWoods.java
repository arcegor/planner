package vkr.planner.model.woods;

import lombok.Data;
import lombok.NoArgsConstructor;
import vkr.planner.model.schedule.RuleType;

import java.util.List;

@Data
@NoArgsConstructor
public class CheckScenarioWoods {
    private List<RuleType> ruleTypes;
    private List<String> kksToInsulate;
    private List<String> neighboringAreasToCheck;
    private Boolean isValidateKksToInsulate;
    private Double minHeightOfWoodsToCreate;
    private Boolean isEmpty;

    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
