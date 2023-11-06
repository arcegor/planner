package vkr.planner.model.specification.woods;

public enum AreaType {
    FLOOR("Перекрытие"),
    WALL("Стена");
    AreaType(String description){
        this.description = description;
    }
    private final String description;
    private String getDescription(){
        return this.description;
    }
}
