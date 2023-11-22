package vkr.planner.model.tea;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vkr.planner.model.schedule.Plan;
@Setter
@Getter
@NoArgsConstructor
public class CheckPlanTea extends Plan {
    private int temp;
    private boolean isMint;
}
