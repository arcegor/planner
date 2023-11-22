package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.RuleType;

@Service
public interface CheckRuleService<R, M> {
    R checkByRule(R r, M m);
    RuleType getRuleType();
}
