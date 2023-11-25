package vkr.planner.service;

import org.springframework.stereotype.Service;

@Service
public interface CheckRuleService<R, M> {
    R checkByRule(R r, M m);
    String getRuleType();
}
