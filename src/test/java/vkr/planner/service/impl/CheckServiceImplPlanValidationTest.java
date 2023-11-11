package vkr.planner.service.impl;

import org.junit.jupiter.api.Test;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanValidationRule;
import vkr.planner.model.schedule.Task;
import vkr.planner.service.impl.rules.RulesCheckServiceImplPlanValidation;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckServiceImplPlanValidationTest {
    private final RulesCheckServiceImplPlanValidation checkService = new RulesCheckServiceImplPlanValidation();

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

        PlanValidationRule order = new PlanValidationRule(PlanValidationRule.PlanValidationRuleType.ORDER);
        PlanValidationRule duration = new PlanValidationRule(PlanValidationRule.PlanValidationRuleType.DURATION);
        PlanValidationRule costs = new PlanValidationRule(PlanValidationRule.PlanValidationRuleType.COSTS);

        assertTrue(checkService.isPlanValidByRule(plan1, order));
        assertTrue(checkService.isPlanValidByRule(plan1, duration));
        assertTrue(checkService.isPlanValidByRule(plan1, costs));

        assertFalse(checkService.isPlanValidByRule(plan2, order));
        assertTrue(checkService.isPlanValidByRule(plan2, duration));
        assertFalse(checkService.isPlanValidByRule(plan2, costs));

        assertTrue(checkService.isPlanValidByRule(plan3, order));
        assertFalse(checkService.isPlanValidByRule(plan3, duration));
        assertTrue(checkService.isPlanValidByRule(plan3, costs));
    }
}