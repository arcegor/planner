package vkr.planner.service;

import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.TechnicalDescription;

@Service
public interface CheckRuleService<TechnicalDescription> {
    Plan checkByRule(Plan plan, TechnicalDescription technicalDescription);
    String getRuleType();
}
