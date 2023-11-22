package vkr.planner.model.schedule;

import org.apache.poi.ss.formula.functions.T;

public enum TaskType{
    VALIDATE_KKS("Проверка кодов ККС"),
    ENCAPSULATION("Герметизация"),
    CREATION_WOODS("Установка лесов"),
    THERMAL_INSULATION("Теплоизоляция"),
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
