package vkr.planner.model.desk;

import lombok.Getter;
import lombok.Setter;
import vkr.planner.model.schedule.TechnicalDescription;
@Getter
@Setter
public class TechnicalDescriptionDesk extends TechnicalDescription {
    private int temp;
    private int clothes;
}
