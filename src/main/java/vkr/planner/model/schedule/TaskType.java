package vkr.planner.model.schedule;

public enum TaskType{
    ПРОВЕРКА_КОДОВ_ККС("Проверка кодов ККС"),
    ГЕРМЕТИЗАЦИЯ("Герметизация"),
    УСТАНОВКА_ЛЕСОВ("Расчет количества устанавливаемых лесов"),
    ТЕПЛОИЗОЛЯЦИЯ("Теплоизоляция"),
    ПОДГОТОВИТЬ_КРУЖКИ("Подготовить кружки"),
    ВСКИПЯТИТЬ_ВОДУ("Вскипятить воду"),
    НАЛИТЬ_ЗАВАРКУ("Налить заварку"),
    ПОЛОЖИТЬ_МЯТУ("Положить мяту"),
    НАЛИТЬ_КИПЯТОК("Налить кипяток");

    TaskType(String name){
        this.name = name;
    }
    public final String name;
    public String getName(){
        return this.name;
    }
    public static TaskType getEnum(String value) {
        for(TaskType v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
