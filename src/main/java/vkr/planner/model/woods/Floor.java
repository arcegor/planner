package vkr.planner.model.woods;

import lombok.Setter;

import java.util.List;

@Setter
public class Floor extends Area{

    private double level;
    public Floor(String description) {
        super(description);
    }
    @Override
    public void parseArea() {
    }
}
