package vkr.planner.model.tea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vkr.planner.model.schedule.RuleType;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalDescriptionTea {
    private int volume;
    private int temp;
    private int mintCount;
    private Map<RuleType, String> ruleTypeMapResult = new HashMap<>();

    public TechnicalDescriptionTea(int volume, int temp) {
        this.volume = volume;
        this.temp = temp;
    }
}
