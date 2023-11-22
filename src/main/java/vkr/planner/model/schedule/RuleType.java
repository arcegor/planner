package vkr.planner.model.schedule;

public enum RuleType { // Набор правил
    KKS("Коды ККС теплоизолируемых проходок"),
    AREA("Проходки со смежными помещениями"),
    LEVEL("Минимальная высота проходки для установки лесов"),
    ТЕМПЕРАТУРА_ВОДЫ("Температура воды"),
    НАЛИЧИЕ_МЯТЫ("Наличие мяты"),
    UNKNOWN("Неизвестное правило");

    RuleType(String description){
        this.description = description;
    }
    public final String description;
}
