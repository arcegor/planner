package vkr.planner.model.schedule;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Plan {
    public List<Task> taskList = new ArrayList<>();
    public Map<String, Object> params = new HashMap<>();
    protected Map<String, String> ruleResult = new HashMap<>();
    private boolean isEmpty;
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }
}
