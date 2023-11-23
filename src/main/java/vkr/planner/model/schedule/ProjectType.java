package vkr.planner.model.schedule;

public enum ProjectType {
    ЗАВАРИВАНИЕ_ЧАЯ("Завариание чая"),
    ВАРКА_БОРЩА("Варка борща"),
    ГЕРМЕТИЗАЦИЯ_ТРУБЫ("Герметизация");

    public final String description;

    ProjectType(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
    public static ProjectType getEnum(String value) {
        for(ProjectType v : values())
            if(v.getDescription().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
