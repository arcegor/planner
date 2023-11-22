package vkr.planner.model.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public abstract class TechnicalDescription {

    protected Map<RuleType, String> ruleTypeResult = new HashMap<>();

}
