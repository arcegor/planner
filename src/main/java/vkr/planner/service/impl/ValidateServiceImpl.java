package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.Request;
import vkr.planner.model.schedule.*;
import vkr.planner.service.ValidateService;
import vkr.planner.service.ProjectService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ValidateServiceImpl implements ValidateService {
    public static final String NO_CONDITIONS = "Не передано никаких условий";
    public static final String UNKNOWN_PROJECT_TYPE = "Неизвестный тип проекта";
    private final RuleTypeMapper ruleTypeMapper;
    private final PlanBuilder planBuilder;
    private final ProjectService projectService;
    @Value("${project.file.name}")
    public String projectFileName;
    @Value("${technical.conditions.file.name}")
    public String technicalConditionsFileName;

    public ValidateServiceImpl(RuleTypeMapper ruleTypeMapper,
                               PlanBuilder planBuilder,
                               ProjectService projectService) {
        this.ruleTypeMapper = ruleTypeMapper;
        this.planBuilder = planBuilder;
        this.projectService = projectService;
    }

    @Override
    public String validateProject(Request request) throws IOException, InvalidFormatException, UnknownTypeException {

        Map<String, InputStream> inputStreamMap = FileUtils.getInput(request.getRequestFile());

        String projectType = request.getProjectType();

        Project project = projectService.getProjectByType(projectType).orElse(null);

        if (project == null) throw new UnknownTypeException(UNKNOWN_PROJECT_TYPE);

        RequestProject requestProject = new RequestProject();

        Plan plan = planBuilder.build(
                ExcelUtils.getObjectsListFromExcelFile(
                        inputStreamMap.get(technicalConditionsFileName), Condition.class),
                ExcelUtils.getObjectsListFromExcelFile(
                        inputStreamMap.get(projectFileName), Task.class)
        );

        requestProject.setPlan(plan);
        requestProject.setId(project.getId());
        requestProject.setType(project.getType());
        requestProject.setTasks(project.getTasks());

        if (requestProject.getPlan().getIsEmpty())
            return NO_CONDITIONS;

        implementRules(requestProject);
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        return getResult(requestProject);
    }
    public void implementRules(RequestProject requestProject) throws UnknownTypeException {
        for (Condition condition : requestProject.getPlan().getTasks()
                .stream()
                .flatMap(task -> task.getConditions().stream())
                .toList()){
             requestProject.setPlan(ruleTypeMapper.getRulesCheckServiceByRuleType(condition.getType())
                    .validateByRule(requestProject.getPlan()));
        }
    }
    public String getResult(RequestProject requestProject){
        return requestProject.getPlan().getConditions().stream()
                .map(Condition::getResult)
                .collect(Collectors.joining("\n"));
    }
}
