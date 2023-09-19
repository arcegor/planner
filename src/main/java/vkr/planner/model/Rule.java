package vkr.planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rule{
    private String name;
    private RuleType ruleType;

    public Rule(String name){
        this.name = name;
    }
    public enum RuleType{
        ORDER,
        COSTS,
        DURATION
    }
}

