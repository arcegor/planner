package vkr.planner.model.type2;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;


@Data
@NoArgsConstructor
public class Plan {
    private String name;
    private List<Task> taskList;
    private boolean isValid;
    private Duration maxDuration; // в днях
    private int maxCosts;

    public Plan(String name){
        this.name = name;
    }
}
