package vkr.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vkr.planner.model.db.Project;
import vkr.planner.model.db.Task;


@Setter
@Getter
public class RequestProject extends Project { // Проект
    public RequestProject(){
        super();
    }

    public Plan plan = new Plan(); // План проверок по проекту

    public ValidationResult validationResult;

    public boolean contains(Task task){
        return plan.getTasks().stream()
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
}
