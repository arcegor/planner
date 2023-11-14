package vkr.planner.model.tea;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vkr.planner.model.schedule.PlanValidationRule;

import java.io.Serializable;
import java.util.List;
@Data
public class TeaRuleSet implements Serializable{
    @JsonProperty(value = "Температура воды")
    public Integer temp;
    @JsonProperty(value = "Наличие мяты")
    public Boolean isMint;
    }


