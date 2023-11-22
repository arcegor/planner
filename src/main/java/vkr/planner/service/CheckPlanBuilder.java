package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.ProjectType;

@Service
public interface CheckPlanBuilder<P, R> {
     ProjectType getPlanType();
     P build(R ruleSet, Project project);
}
