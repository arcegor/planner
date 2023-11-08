package vkr.planner.model.woods;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Wall extends Area{

    private double level, top;
    public Wall(AreaType description) {
        super(description);
    }
}
