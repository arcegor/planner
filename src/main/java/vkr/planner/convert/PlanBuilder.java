package vkr.planner.convert;

import com.google.common.collect.ImmutableMap;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.Task;
import vkr.planner.model.schedule.TaskType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Getter
@Setter
public class PlanBuilder {

    private Map<TaskType, String> cellPatternEnumTypeStringMap;
    private Map<Integer, List<String>> excelTable;

    public static Integer ID_INDEX = 0;
    public static Integer TASK_INDEX = 1;
    public static Integer IS_DONE_INDEX = 2;
    public static Integer COSTS_INDEX = 3;
    public static Integer DURATION_INDEX = 4;
    public static Integer IS_BLOCKER_INDEX = 5;

    @PostConstruct
    public void init(){
        this.cellPatternEnumTypeStringMap = ImmutableMap.<TaskType, String>builder()
                .put(TaskType.VALIDATE_KKS, "Проверка кодов ККС")
                .put(TaskType.ENCAPSULATION, "Герметизация")
                .put(TaskType.CREATION_WOODS, "Установка лесов")
                .put(TaskType.THERMAL_INSULATION, "Теплоизоляция")
                .build();
    }
    private Optional<TaskType> parseCell(String cell){
        return cellPatternEnumTypeStringMap.entrySet().stream()
                .filter(e -> !cell.trim().isEmpty())
                .filter(e -> isContainsSubstring(cell, e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }
    private Optional<TaskType> parseRow(List<String> row){
        return row.stream()
                .map(this::parseCell)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
    public boolean isContainsSubstring(String cell, String pattern){
        return cell.contains(pattern);
    }

    public Plan convertMapToPlan(Map<Integer, List<String>> excelTable){
        this.excelTable = excelTable;
        Plan plan = new Plan();
        List<Task> taskList = new ArrayList<>();
        try {
            for (Integer key: excelTable.keySet()){
                Optional<TaskType> cellPatternEnumType = parseRow(excelTable.get(key));
                if (cellPatternEnumType.isEmpty())
                    continue;
                Task task = new Task();
                task.setTaskType(cellPatternEnumType.get());
                taskList.add(task);
            }
            plan.setTaskList(taskList);
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return plan;
    }
}
