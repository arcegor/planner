package vkr.planner.model.woods;

import lombok.Getter;

import java.util.List;

@Getter
abstract public class Area {

    private final String description;

    private List<Pipe> pipeList;

    public Area(String description) {
        this.description = description;
    }
    abstract public void parseArea();
}
