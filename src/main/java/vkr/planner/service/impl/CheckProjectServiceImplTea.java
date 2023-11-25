package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.convert.TechnicalDescriptionTeaBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.RequestProject;
import vkr.planner.model.schedule.Rule;
import vkr.planner.model.schedule.Task;
import vkr.planner.model.tea.PlanTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckProjectService;
import vkr.planner.service.RuleService;
import vkr.planner.service.TaskService;
import vkr.planner.service.impl.plans.PlanTeaBuilder;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Component
public class CheckProjectServiceImplTea implements CheckProjectService {
    public static final String NO_RULES = "Не передано никаких правил";
    public static final String PROJECT_TYPE = "Заваривание чая";
    public final ProjectBuilder projectBuilder;
    private final RuleTypeMapper ruleTypeMapper;
    private final TechnicalDescriptionTeaBuilder technicalDescriptionTeaBuilder;
    private final PlanTeaBuilder planTeaBuilder;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private TaskService taskService;

    @Value(value = "projectFileName")
    public String projectFileName;

    @Value(value = "technicalDescriptionFileName")
    public String technicalDescriptionFileName;

    public CheckProjectServiceImplTea(ProjectBuilder projectBuilder, RuleTypeMapper ruleTypeMapper, TechnicalDescriptionTeaBuilder technicalDescriptionTeaBuilder, PlanTeaBuilder planTeaBuilder) {
        this.projectBuilder = projectBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.technicalDescriptionTeaBuilder = technicalDescriptionTeaBuilder;
        this.planTeaBuilder = planTeaBuilder;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap = FileUtils.getInput(checkRequest.getRequestFile());

        RequestProject requestProject = new RequestProject(PROJECT_TYPE);

        List<Rule> ruleList = ruleService.getAllByProject(requestProject);
        List<Task> taskList = taskService.getAllByProject(requestProject);

        projectBuilder.setTaskSet(taskList);

        requestProject = projectBuilder.convertMapToProject(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get(projectFileName)
        ));

        Map<String, Object> teaRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(),
                Map.class);

        requestProject.setRuleTypes(ruleList);
        requestProject.setPlan(planTeaBuilder.build(teaRuleSet, requestProject));
        requestProject.setTechnicalDescription(convertToModel(stringInputStreamMap.get(technicalDescriptionFileName)));

        if (requestProject.getPlan().getIsEmpty())
            return NO_RULES;

        implementRules(requestProject);

        return getResult(requestProject);
    }
    public TechnicalDescriptionTea convertToModel(InputStream inputStream) throws IOException, InvalidFormatException {
        return technicalDescriptionTeaBuilder.convertMapToTechnicalDescriptionTea(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public void implementRules(RequestProject requestProject) throws UnknownTypeException {
        for (Rule rule: requestProject.getPlan().getRuleTypes()){
             requestProject.setPlan((PlanTea) ruleTypeMapper.getRulesCheckServiceByRuleType(rule.getType())
                    .checkByRule(requestProject.getPlan(), requestProject.getTechnicalDescription()));
        }
    }
    public String getResult(RequestProject requestProject){
        return String.join("\n", requestProject.getPlan().getRuleResult().values());
    }
    @Override
    public String getProjectType() {
        return PROJECT_TYPE;
    }

}
