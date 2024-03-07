package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.Plan;

@Service
public interface ValidateByRuleService {
    Plan validateByRule(Plan plan);
    String getRuleType();
}
