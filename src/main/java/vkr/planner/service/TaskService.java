package vkr.planner.service;

import org.springframework.data.jpa.repository.JpaRepository;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.Task;

import java.util.List;

public interface TaskService extends JpaRepository<Task, Long> {
    List<Task> getAllByProject(Project project);
}
