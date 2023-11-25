package vkr.planner.model.woods;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vkr.planner.model.schedule.Plan;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlanWoods extends Plan {
    private List<String> kksToInsulate;
    private List<String> neighboringAreasToCheck;
    private Boolean isValidateKksToInsulate;
    private Double minHeightOfWoodsToCreate;
}
