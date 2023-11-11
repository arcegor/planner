package vkr.planner.model.woods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vkr.planner.model.schedule.PlanValidationRule;

import java.io.Serializable;
import java.util.List;

@Data
public class WoodsRuleSet implements Serializable {
    @JsonProperty(value = "Валидация плана-графика")
    public List<PlanValidationRule> planValidationRules;
    @JsonProperty(value = "Коды ККС теплоизолируемых проходок")
    public List<String> kksToInsulate;
    @JsonProperty(value = "Проходки со смежными помещениями")
    public List<String> neighboringAreasToCheck;
    @JsonProperty(value = "Нужно ли проверить на наличие коллизий ККС")
    public Boolean isValidateKksToInsulate;
    @JsonProperty(value = "Минимальная высота проходки для установки лесов")
    public Double minHeightOfWoodsToCreate;
    @JsonProperty(value = "test")
    public Boolean test;
}
