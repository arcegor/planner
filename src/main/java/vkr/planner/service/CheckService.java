package vkr.planner.service;

import vkr.planner.model.DocumentDto;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;

public interface CheckService {
    boolean checkPlanByRule(Plan plan, Rule rule);
}
