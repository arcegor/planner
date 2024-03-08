package vkr.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vkr.planner.model.db.Condition;
import vkr.planner.model.db.Project;
import vkr.planner.model.db.Task;

import java.util.List;


@Setter
@Getter
public class Plan extends Project { // Проект
    public Plan(){
        super();
    }

    public List<Task> providedTasks;

    public List<Condition> providedConditions;

    private boolean isEmpty;
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }

    public ValidationResult validationResult;
    public boolean contains(Task task){
        return this.getTasks().stream()
                .anyMatch(t -> t.getType().equals(task.getType()));
    }
    public static class ValidationResult{
        @JsonProperty(value = "resultType")
        private ResultType resultType;
        @JsonProperty(value = "result")
        private String result;
    }
    public enum ResultType{
        VALID,
        NOT_VALID
    }
    public List<Condition> getPlanConditions(){
        return this.getProvidedTasks()
                .stream()
                .filter(task -> !task.getConditions().isEmpty())
                .flatMap(task -> task.getConditions().stream())
                .toList();
    }
    public Boolean isConditionIncludedByPlan(Condition condition){
        return this.getPlanConditions().stream()
                .map(Condition::getType)
                .anyMatch(type -> condition.getType().equals(type));
    }
}
