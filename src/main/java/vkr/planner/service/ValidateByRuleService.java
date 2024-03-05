package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.Plan;

@Service
public interface ValidateByRuleService {
    Plan validateByRule(Plan plan);
    String getRuleType();
}
