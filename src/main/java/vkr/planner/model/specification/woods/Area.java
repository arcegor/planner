package vkr.planner.model.specification.woods;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
abstract public class Area {

    private final AreaType description;

    private final List<Pipe> pipeList = new ArrayList<>();

    private final List<String> neighboringSpaces = new ArrayList<>();
    public Area(AreaType description) {
        this.description = description;
    }
}
