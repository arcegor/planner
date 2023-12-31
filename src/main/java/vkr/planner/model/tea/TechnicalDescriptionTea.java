package vkr.planner.model.tea;

import lombok.*;
import vkr.planner.model.schedule.TechnicalDescription;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalDescriptionTea extends TechnicalDescription {
    private int volume;
    private int temp;
    private int mintCount;
    public TechnicalDescriptionTea(int volume, int i) {
        this.volume = volume;
        this.temp = i;
    }
}
