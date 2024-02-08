package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.RequestProject;
import vkr.planner.model.schedule.Rule;
import vkr.planner.service.CheckProjectService;
import vkr.planner.service.ProjectService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.service.mapper.TechnicalDescriptionMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Component
public class CheckProjectServiceImpl implements CheckProjectService {
    public static final String NO_CONDITIONS = "Не передано никаких условий";
    public static final String UNKNOWN_PROJECT_TYPE = "Неизвестный тип проекта";
    public final ProjectBuilder projectBuilder;
    private final RuleTypeMapper ruleTypeMapper;
    private final TechnicalDescriptionMapper technicalDescriptionMapper;
    private final PlanBuilder planBuilder;
    @Autowired
    private ProjectService projectService;
    @Value("${projectFileName}")
    public String projectFileName;

    @Value("${technicalDescriptionFileName}")
    public String technicalDescriptionFileName;

    public CheckProjectServiceImpl(ProjectBuilder projectBuilder, RuleTypeMapper ruleTypeMapper,
                                   TechnicalDescriptionMapper technicalDescriptionMapper, PlanBuilder planBuilder) {
        this.projectBuilder = projectBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.technicalDescriptionMapper = technicalDescriptionMapper;
        this.planBuilder = planBuilder;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap = FileUtils.getInput(checkRequest.getRequestFile());
        String projectType = checkRequest.getProjectType();

        Project project = projectService.getProjectByType(projectType).orElse(null);

        if (project == null) throw new UnknownTypeException(UNKNOWN_PROJECT_TYPE);

        projectBuilder.setTaskSet(project.getTasks());

        RequestProject requestProject = projectBuilder.build(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get(projectFileName)
        ));

        requestProject.setId(project.getId());
        requestProject.setType(project.getType());
        requestProject.setRequestTasks(project.getTasks().stream()
                .filter(requestProject::contains)
                .toList());

        Map<String, Object> conditionSet = JsonUtils.parseJsonToObject(checkRequest.getProjectCondition(),
                Map.class);

        requestProject.setPlan(planBuilder.build(conditionSet, requestProject));

        requestProject.setTechnicalDescription(technicalDescriptionMapper.getBuilderByProjectType(projectType)
                        .convertMapToTechnicalDescription(
                                ExcelUtils.parseExcelFromInputStreamToMap(stringInputStreamMap
                                        .get(technicalDescriptionFileName))));

        if (requestProject.getPlan().getIsEmpty())
            return NO_CONDITIONS;

        implementRules(requestProject);

        return getResult(requestProject);
    }
    public void implementRules(RequestProject requestProject) throws UnknownTypeException {
        for (Rule rule: requestProject.getPlan().getTaskList()
                .stream()
                .flatMap(task -> task.getRules().stream())
                .toList()){
             requestProject.setPlan(ruleTypeMapper.getRulesCheckServiceByRuleType(rule.getType())
                    .checkByRule(requestProject.getPlan(), requestProject.getTechnicalDescription()));
        }
    }
    public String getResult(RequestProject requestProject){
        return String.join("\n", requestProject.getPlan().getRuleResult().values());
    }
}
