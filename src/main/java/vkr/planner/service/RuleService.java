package vkr.planner.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.Rule;

import java.util.List;

@Repository
public interface RuleService extends JpaRepository<Rule, Long> {
    List<Rule> getAllByProject(Project project);
}
