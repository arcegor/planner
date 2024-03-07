package vkr.planner.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import vkr.planner.model.db.Condition;
import vkr.planner.model.db.Task;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Plan {

    public List<Task> tasks;

    private boolean isEmpty;
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
