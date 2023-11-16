package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.RuleType;

@Service
public interface CheckRuleService<R, M> {
    M checkByRule(R r, M m);
    RuleType getRuleType();
}
