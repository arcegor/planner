package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.Plan;
import vkr.planner.model.Request;
import vkr.planner.model.db.*;
import vkr.planner.service.ValidationService;
import vkr.planner.repository.ProjectRepository;
import vkr.planner.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Component
public class ValidationServiceImpl implements ValidationService {
    public static final String UNKNOWN_PROJECT_TYPE = "Неизвестный тип проекта";
    @Autowired
    private RuleTypeMapper ruleTypeMapper;
    @Autowired
    private ProjectRepository projectRepository;
    @Value("${project.file.name}")
    public String projectFileName;
    @Value("${technical.conditions.file.name}")
    public String technicalConditionsFileName;

    @Override
    public Plan validateProject(Request request) throws IOException, InvalidFormatException, UnknownTypeException {

        Plan plan = preprocessRequest(request);

        implementRules(plan);

        return plan;
    }

    public Plan preprocessRequest(Request request) throws IOException, UnknownTypeException, InvalidFormatException {

        Map<String, InputStream> inputStreamMap = FileUtils.getInput(request.getRequestFile());

        String projectType = request.getProjectType();

        Project project = projectRepository.getProjectByType(projectType).orElse(null);

        if (project == null) throw new UnknownTypeException(UNKNOWN_PROJECT_TYPE);

        Plan plan = new Plan();

        plan.setId(project.getId());
        plan.setType(project.getType());
        plan.setTasks(project.getTasks());
        plan.setProvidedTasks(ExcelUtils.getObjectsListFromExcelFile(
                inputStreamMap.get(projectFileName), Task.class));
        plan.setProvidedConditions(ExcelUtils.getObjectsListFromExcelFile(
                inputStreamMap.get(technicalConditionsFileName), Condition.class));
        checkPresentedTasksAndConditionsInPlan(plan);

        return plan;
    }
    public void implementRules(Plan plan) throws UnknownTypeException {
        for (Condition condition : plan.getPlanConditions()){
             ruleTypeMapper.getRulesCheckServiceByRuleType(
                     condition.getType())
                    .applyRule(plan, condition);
        }
    }
    public void checkPresentedTasksAndConditionsInPlan(Plan plan){
        for (Task task: plan.getTasks()){
            task.setIsPresentByPlan(task.containsInTasks(plan.getProvidedTasks()));
            for (Condition condition: task.getConditions()){
                condition.setIsPresentByPlan(plan.isConditionIncludedByPlan(condition));
            }
        }
    }
}
