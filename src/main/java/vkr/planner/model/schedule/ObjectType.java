package vkr.planner.model.schedule;

public enum ObjectType {
    РЕАКТОР("РЕАКТОР"),
    КУХНЯ("КУХНЯ");

    ObjectType(String description) {
        this.description = description;
    }

    public final String description;
    public String getDescription(){
        return this.description;
    }
    public static ObjectType getEnum(String value) {
        for(ObjectType v : values())
            if(v.getDescription().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
