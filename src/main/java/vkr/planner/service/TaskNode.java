package vkr.planner.service;

import vkr.planner.model.schedule.Condition;
import vkr.planner.model.schedule.Task;

import java.util.List;

public class TaskNode {

    private Task task; // текущая задача
    private Task nextTask; // следующая задача
    private List<Condition> conditions; // условия перехода к следующей задаче
}
