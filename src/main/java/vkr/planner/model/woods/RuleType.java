package vkr.planner.model.woods;

public enum RuleType {
    PLAN_VALIDATION("Валидация плана-графика"),
    KKS("Коды ККС теплоизолируемых проходок"),
    AREA("Проходки со смежными помещениями"),
    LEVEL("Минимальная высота проходки для установки лесов"),
    UNKNOWN("Неизвестное правило");
    RuleType(String description){
        this.description = description;
    }
    public final String description;
}
