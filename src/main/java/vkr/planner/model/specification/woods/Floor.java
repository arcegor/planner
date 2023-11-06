package vkr.planner.model.specification.woods;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Floor extends Area{

    private double level;
    public Floor(AreaType description) {
        super(description);
    }
}
