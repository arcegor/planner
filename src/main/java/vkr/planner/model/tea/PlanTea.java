package vkr.planner.model.tea;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vkr.planner.model.schedule.Plan;
@Setter
@Getter
@NoArgsConstructor
public class PlanTea extends Plan {
    public boolean getIsMint(String isMint){
        return isMint.equalsIgnoreCase("да");
    }
}
