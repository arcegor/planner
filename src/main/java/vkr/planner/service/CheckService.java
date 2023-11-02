package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
@Service
public interface CheckService {
    boolean checkPlanByRule(Plan plan, Rule rule);
    String check();
    String getRequestType();
}
