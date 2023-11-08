package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.woods.WoodsRuleSet;
@Service
public interface RulesCheckService<T> {
    String checkByRule(WoodsRuleSet woodsRuleSet, T t);
    String getRuleType();
}
