package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.Plan;
import vkr.planner.model.db.Condition;

@Service
public interface RuleService {
    Plan applyRule(Plan plan, Condition condition);
    String getRuleType();
}
