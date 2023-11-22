package vkr.planner.convert;

import com.google.common.collect.ImmutableMap;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.map.SingletonMap;
import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.Task;
import vkr.planner.model.schedule.TaskType;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class ProjectBuilder {

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
                .putAll(Arrays.stream(TaskType.values()).distinct()
                        .collect(Collectors.toMap(value -> value, Enum::name)))
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

    public Project convertMapToProject(Map<Integer, List<String>> excelTable){
        this.excelTable = excelTable;
        Project project = new Project();
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
            project.setTaskList(taskList);
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return project;
    }
}
