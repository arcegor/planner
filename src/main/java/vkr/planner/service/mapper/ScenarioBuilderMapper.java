package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.schedule.PlanType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.service.CheckScenarioBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScenarioBuilderMapper {
    public static final RuleType UNKNOWN_TYPE_MESSAGE = RuleType.UNKNOWN;
    @Autowired
    private final List<CheckScenarioBuilder> checkScenarioBuilders;
    private Map<PlanType, CheckScenarioBuilder> planTypeCheckScenarioBuilderMap;

    public ScenarioBuilderMapper(List<CheckScenarioBuilder> checkScenarioBuilders) {
        this.checkScenarioBuilders = checkScenarioBuilders;
    }

    @PostConstruct
    public void init() {
        planTypeCheckScenarioBuilderMap = checkScenarioBuilders.stream()
                .collect(Collectors.toMap(CheckScenarioBuilder::getPlanType, Function.identity()));
    }
    @NotNull
    public CheckScenarioBuilder getCheckScenarioBuilderByPlanType(PlanType planType) throws UnknownTypeException {
        return Optional.ofNullable(planTypeCheckScenarioBuilderMap.get(planType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE.description));
    }
}
