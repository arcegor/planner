package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.schedule.ProjectType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.service.CheckPlanBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlanBuilderMapper {
    public static final RuleType UNKNOWN_TYPE_MESSAGE = RuleType.UNKNOWN;
    @Autowired
    private final List<CheckPlanBuilder> checkPlanBuilders;
    private Map<ProjectType, CheckPlanBuilder> projectTypeCheckPlanBuilderMap;

    public PlanBuilderMapper(List<CheckPlanBuilder> checkPlanBuilders) {
        this.checkPlanBuilders = checkPlanBuilders;
    }

    @PostConstruct
    public void init() {
        projectTypeCheckPlanBuilderMap = checkPlanBuilders.stream()
                .collect(Collectors.toMap(CheckPlanBuilder::getProjectType, Function.identity()));
    }
    @NotNull
    public CheckPlanBuilder getCheckPlanBuilderByPlanType(ProjectType projectType) throws UnknownTypeException {
        return Optional.ofNullable(projectTypeCheckPlanBuilderMap.get(projectType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE.description));
    }
}
