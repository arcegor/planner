package vkr.planner.model.schedule;

public enum ObjectType {
    РЕАКТОР("Реактор"),
    КУХНЯ("Кухня");

    ObjectType(String description) {
        this.description = description;
    }
    public final String description;
}
