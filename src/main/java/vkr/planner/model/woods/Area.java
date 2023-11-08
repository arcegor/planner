package vkr.planner.model.woods;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
abstract public class Area {

    protected AreaType description;

    protected final List<Pipe> pipeList = new ArrayList<>();

    private final List<String> neighboringSpaces = new ArrayList<>();
    public Area(AreaType description) {
        this.description = description;
    }

    abstract public double getLevel();
}
