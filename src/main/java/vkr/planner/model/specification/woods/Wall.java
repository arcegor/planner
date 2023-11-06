package vkr.planner.model.specification.woods;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wall extends Area{

    private double bottom, top;
    public Wall(AreaType description) {
        super(description);
    }

}
