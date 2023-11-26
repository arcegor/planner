package vkr.planner.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vkr.planner.model.schedule.Project;

@Repository
public interface ProjectService extends JpaRepository<Project, Long> {
    Project getProjectByProjectType(String projectType);
    boolean existsProjectByProjectType(String projectType);
}
