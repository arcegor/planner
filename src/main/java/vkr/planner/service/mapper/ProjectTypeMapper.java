package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.service.CheckProjectService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectTypeMapper {
    public static final String UNKNOWN_TYPE_MESSAGE = "Неизвестный тип проекта!";
    @Autowired
    private final List<CheckProjectService> projectTypes;

    private Map<String, CheckProjectService> projectTypeMappingCheckService;

    public ProjectTypeMapper(List<CheckProjectService> projectTypes) {
        this.projectTypes = projectTypes;
    }

    @PostConstruct
    public void init() {
        projectTypeMappingCheckService = projectTypes.stream()
                .collect(Collectors.toMap(CheckProjectService::getProjectType, Function.identity()));
    }
    @NotNull
    public CheckProjectService getCheckServiceByProjectType(String projectType) throws UnknownTypeException {
        return Optional.ofNullable(projectTypeMappingCheckService.get(projectType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE));
    }
}
