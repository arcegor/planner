package vkr.planner.model.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Plan {

    public List<Task> tasks = new ArrayList<>();

    public List<Condition> conditions = new ArrayList<>();
    private boolean isEmpty;
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
