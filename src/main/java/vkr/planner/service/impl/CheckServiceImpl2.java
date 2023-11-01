package vkr.planner.service.impl;

import org.springframework.stereotype.Service;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
import vkr.planner.service.CheckService;

public class CheckServiceImpl2 implements CheckService {
    public static final String REQUEST_TYPE = "plan2";
    @Override
    public boolean checkPlanByRule(Plan plan, Rule rule) {
        return false;
    }
    @Override
    public boolean check() {
        return true;
    }
    @Override
    public CheckService getInstance(String requestType) {
        if (requestType.equals(REQUEST_TYPE)) return new CheckServiceImpl2();
        return null;
    }
}
