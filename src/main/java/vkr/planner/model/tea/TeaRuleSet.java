package vkr.planner.model.tea;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeaRuleSet implements Serializable{
    @JsonProperty(value = "Температура воды")
    public Integer temp;
    @JsonProperty(value = "Наличие мяты")
    public String isMint;
}


