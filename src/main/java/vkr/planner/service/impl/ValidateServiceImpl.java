package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.Plan;
import vkr.planner.model.Request;
import vkr.planner.model.RequestProject;
import vkr.planner.model.db.*;
import vkr.planner.service.ValidateService;
import vkr.planner.repository.ProjectRepository;
import vkr.planner.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ValidateServiceImpl implements ValidateService {
    public static final String UNKNOWN_PROJECT_TYPE = "Неизвестный тип проекта";
    private final RuleTypeMapper ruleTypeMapper;
    private final PlanBuilder planBuilder;
    private final ProjectRepository projectRepository;
    @Value("${project.file.name}")
    public String projectFileName;
    @Value("${technical.conditions.file.name}")
    public String technicalConditionsFileName;

    public ValidateServiceImpl(RuleTypeMapper ruleTypeMapper,
                               PlanBuilder planBuilder,
                               ProjectRepository projectRepository) {
        this.ruleTypeMapper = ruleTypeMapper;
        this.planBuilder = planBuilder;
        this.projectRepository = projectRepository;
    }

    @Override
    public RequestProject validateProject(Request request) throws IOException, InvalidFormatException, UnknownTypeException {

        RequestProject requestProject = preprocessRequest(request);

        implementRules(requestProject);

        return requestProject;
    }

    public RequestProject preprocessRequest(Request request) throws IOException, UnknownTypeException, InvalidFormatException {

        Map<String, InputStream> inputStreamMap = FileUtils.getInput(request.getRequestFile());

        String projectType = request.getProjectType();

        Project project = projectRepository.getProjectByType(projectType).orElse(null);

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
        return requestProject;
    }
    public void implementRules(RequestProject requestProject) throws UnknownTypeException {
        for (Condition condition : requestProject.getPlan().getTasks()
                .stream()
                .flatMap(task -> task.getConditions().stream())
                .toList()){
             requestProject.setPlan(ruleTypeMapper.getRulesCheckServiceByRuleType(
                     condition.getType())
                    .validateByRule(requestProject.getPlan()));
        }
    }
}
