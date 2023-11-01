package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.DocumentDto;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
public interface CheckService {
    boolean checkPlanByRule(Plan plan, Rule rule);
    boolean check();
    CheckService getInstance(String requestType);
}
