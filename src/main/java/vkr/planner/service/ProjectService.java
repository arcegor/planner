package vkr.planner.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vkr.planner.model.schedule.Project;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectService extends JpaRepository<Project, UUID> {
    Optional<Project> getProjectByType(String projectType);
}
