package vkr.planner.model.tea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vkr.planner.model.RuleType;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teapot {
    private double volume;
    private int temp;
    private Map<RuleType, String> ruleTypeMapResult = new HashMap<>();

    public Teapot(double v, int i) {
        this.volume = v;
        this.temp = i;
    }
}
