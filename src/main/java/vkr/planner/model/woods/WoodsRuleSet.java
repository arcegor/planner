package vkr.planner.model.woods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WoodsRuleSet implements Serializable {
    @JsonProperty(value = "Коды ККС теплоизолируемых проходок")
    public List<String> kksToInsulate;
    @JsonProperty(value = "Проходки со смежными помещениями")
    public List<String> neighboringAreasToCheck;
    @JsonProperty(value = "Минимальная высота проходки для установки лесов")
    public Double minHeightOfWoodsToCreate;
}
