package vkr.planner.model.woods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WoodsRuleSet implements Serializable {
    @JsonProperty(value = "Коды ККС")
    private List<String> kksToInsulate;
    @JsonProperty(value = "Смежные помещения")
    private List<String> neighboringAreasToCheck;
    @JsonProperty(value = "Нужно ли проверить на наличие коллизий ККС")
    private boolean isValidateKksToInsulate;
    @JsonProperty(value = "Высота лесов")
    private double woodsTopBound;

}
