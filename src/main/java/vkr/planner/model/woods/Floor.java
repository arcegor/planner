package vkr.planner.model.woods;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Floor extends Area{

    private double level;
    public Floor(AreaType description) {
        super(description);
    }
}
