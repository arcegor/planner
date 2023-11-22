package vkr.planner.model.schedule;

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
}
