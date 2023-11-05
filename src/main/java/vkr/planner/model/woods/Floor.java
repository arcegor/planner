package vkr.planner.model.woods;

import lombok.Setter;

import java.util.List;
import java.util.regex.Pattern;

@Setter
public class Floor extends Area{

    private double level;

    private Pattern levelPattern = Pattern.compile(".*");
    public Floor(String description) {
        super(description);
    }
}
