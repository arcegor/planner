package vkr.planner.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
import vkr.planner.model.Task;
import vkr.planner.service.CheckService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CheckServiceImplTest {
    private final CheckService checkService = new CheckServiceImpl();

    @Test
    void checkPlanByRule() {

        Task task1 = new Task("Приготовить кружки", 1, Duration.ofDays(1), true, 1 );
        Task task2 = new Task("Вскипятить воду", 2, Duration.ofDays(2), true, 2 );
        Task task3 = new Task("Заварить чай", 3, Duration.ofDays(3), true, 3 );
        Task task4 = new Task("Приготовить что-нибудь сладкое к чаю", 4, Duration.ofDays(4), true, 4 );
        Task task5 = new Task("Попить чай", 5, Duration.ofDays(5), true, 5 );

        Plan plan1 = new Plan("Первый");
        plan1.setTaskList(Arrays.asList(task1, task2, task3, task4, task5)); // Все правильно
        plan1.setMaxCosts(15);
        plan1.setMaxDuration(Duration.ofDays(15));

        Plan plan2 = new Plan("Второй");
        plan2.setTaskList(Arrays.asList(task2, task1, task4, task5, task3)); // Ошибка в порядке задач
        plan2.setMaxCosts(11); // Превышение стоимости
        plan2.setMaxDuration(Duration.ofDays(15));

        Plan plan3 = new Plan("Третий");
        plan3.setTaskList(Arrays.asList(task1, task3, task4, task5));
        plan3.setMaxCosts(15);
        plan3.setMaxDuration(Duration.ofDays(11)); // Превышение длительности

        Rule order = new Rule("Порядок", Rule.RuleType.ORDER);
        Rule duration = new Rule("Время", Rule.RuleType.DURATION);
        Rule costs = new Rule("Стоимость", Rule.RuleType.COSTS);

        assertTrue(checkService.checkPlanByRule(plan1, order));
        assertTrue(checkService.checkPlanByRule(plan1, duration));
        assertTrue(checkService.checkPlanByRule(plan1, costs));

        assertFalse(checkService.checkPlanByRule(plan2, order));
        assertTrue(checkService.checkPlanByRule(plan2, duration));
        assertFalse(checkService.checkPlanByRule(plan2, costs));

        assertTrue(checkService.checkPlanByRule(plan3, order));
        assertFalse(checkService.checkPlanByRule(plan3, duration));
        assertTrue(checkService.checkPlanByRule(plan3, costs));
    }
}