package vkr.planner.convert;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import vkr.planner.model.schedule.RequestProject;
import vkr.planner.model.schedule.Task;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Getter
@Setter
public class ProjectBuilder {

    private List<Task> taskSet;
    private Map<Integer, List<String>> excelTable;

    public static Integer ID_INDEX = 0;
    public static Integer TASK_INDEX = 1;
    public static Integer IS_DONE_INDEX = 2;
    public static Integer COSTS_INDEX = 3;
    public static Integer DURATION_INDEX = 4;
    public static Integer IS_BLOCKER_INDEX = 5;
    public static Integer DATE_INDEX = 6;

    private Optional<Task> parseCell(String cell){
        return taskSet.stream()
                .filter(e -> !cell.trim().isEmpty())
                .filter(e -> isContainsSubstring(cell, e.getType()))
                .findFirst();
    }
    private Optional<Task> parseRow(List<String> row){
        return row.stream()
                .map(this::parseCell)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
    public boolean isContainsSubstring(String cell, String pattern){
        return cell.contains(pattern);
    }

    public RequestProject convertMapToProject(Map<Integer, List<String>> excelTable){
        this.excelTable = excelTable;
        RequestProject requestProject = new RequestProject();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<Task> taskList = new ArrayList<>();
        try {
            for (Integer key: excelTable.keySet()){
                Optional<Task> cellPatternEnumType = parseRow(excelTable.get(key));
                if (cellPatternEnumType.isEmpty())
                    continue;
                Task task = new Task();
                task.setType(excelTable.get(key).get(TASK_INDEX));
                //task.setDate(formatter.parse(excelTable.get(key).get(DATE_INDEX)));
                taskList.add(task);
            }
            requestProject.setTaskList(taskList);
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return requestProject;
    }
}
