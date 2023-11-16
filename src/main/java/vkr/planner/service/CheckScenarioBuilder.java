package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanType;

@Service
public interface CheckScenarioBuilder<P, R> {
     PlanType getPlanType();
     P build(R ruleSet, Plan plan);
}
