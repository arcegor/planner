package vkr.planner.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
import vkr.planner.service.CheckService;

@Component
public class CheckServiceImpl2 implements CheckService {
    public static final String REQUEST_TYPE = "Второй";

    public static final String CHECK_RESPONSE = "Проверка запроса пройдена!";
    @Override
    public boolean checkPlanByRule(Plan plan, Rule rule) {
        return false;
    }
    @Override
    public String check() {
        return CHECK_RESPONSE;
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
    }
}
