package vkr.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vkr.planner.model.db.Condition;
import vkr.planner.model.db.Project;
import vkr.planner.model.db.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    public List<ValidationResult> validationResult = new ArrayList<>();

    public String getResult(){
        return this.getValidationResult().stream()
                .map(validationResult1 -> validationResult1.result)
                .collect(Collectors.joining(" "));
    }
    public boolean containsInPlan(Task task){
        return this.getProvidedTasks().stream()
                .anyMatch(t -> t.getType().equals(task.getType()));
    }
    public static class ValidationResult{
        @JsonProperty(value = "resultType")
        private ResultType resultType;
        @JsonProperty(value = "result")
        private String result;
        public ValidationResult(ResultType resultType, String result){
            this.resultType = resultType;
            this.result = result;
        }
        @Override
        public String toString(){
            return result;
        }
    }
    public enum ResultType{
        VALID,
        NOT_VALID,
        STATUS
    }
    public Task getTaskByCondition(Condition condition){
        for (Task task: this.getTasks()){
            if (task.getConditions().stream()
                    .map(Condition::getType)
                    .anyMatch(a -> a.equals(condition.getType())))
                return task;
        }
        return null;
    }
    public int indexOfTaskInPlan(Task task){
        for (Task t: this.getProvidedTasks()){
            if (t.equals(task))
                return t.getOrderByPlan();
        }
        return -1;
    }

    public boolean validateProvidedTasks(){
        for (Task task: this.getProvidedTasks()){
            for (Task task1: this.getTasks()){
                if (task1.getType().equals(task.getType()))
                    return true;
            }
        }
        return false;
    }
}
