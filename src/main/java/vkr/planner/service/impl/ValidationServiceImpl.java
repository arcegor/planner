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
    public static final String UNKNOWN_CONDITION_TYPE = "Передано неизвестное условие!";
    public static final String UNKNOWN_TASK_TYPE = "Передана неизвестная задача!";
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final ProjectRepository projectRepository;
    @Value("${project.file.name}")
    public String projectFileName;
    @Value("${technical.conditions.file.name}")
    public String technicalConditionsFileName;

    public ValidationServiceImpl(RuleTypeMapper ruleTypeMapper, ProjectRepository projectRepository) {
        this.ruleTypeMapper = ruleTypeMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public Plan validateProject(Request request) throws IOException, InvalidFormatException, UnknownTypeException {

        Plan plan = preprocessRequest(request);

        return validatePlan(plan);
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
        plan.setConditions(project.getConditions());

        plan.setProvidedTasks(ExcelUtils.getObjectsListFromExcelFile(
                inputStreamMap.get(projectFileName), Task.class));

        plan.setProvidedConditions(ExcelUtils.getObjectsListFromExcelFile(
                inputStreamMap.get(technicalConditionsFileName), Condition.class));

        if (!plan.validateProvidedTasks())
            throw new UnknownTypeException(UNKNOWN_TASK_TYPE);

        if (!plan.validateProvidedConditions())
            throw new UnknownTypeException(UNKNOWN_CONDITION_TYPE);

        checkPresentedTasksInPlan(plan);

        implementRules(plan);

        return plan;
    }
    public void implementRules(Plan plan) throws UnknownTypeException {
        for (Condition condition : plan.getProvidedConditions()){
             ruleTypeMapper.getRuleServiceByRuleType(
                     condition.getType())
                    .applyRule(plan, condition);
        }
    }
    public void checkPresentedTasksInPlan(Plan plan){
        for (Task task: plan.getTasks()){
            int index = plan.indexOfTaskInPlan(task);
            if (index != -1){
                task.setPresentedByPlan(true);
            }
            task.setOrderByPlan(index);
        }
    }
    public void validateTasks(Plan plan){

        int shift = 0;

        for (Task task: plan.getTasks()){

            task.setValid(true);

            int orderByProject = task.getOrder();
            int orderByPlan = task.getOrderByPlan();
            boolean providedByRule = task.isProvidedByRule();
            boolean presentedByPlan = task.getPresentedByPlan();
            boolean requiredByRule = task.isRequiredByRule();

            if (orderByPlan == -1)
                shift = shift + 1;
            else orderByPlan = orderByPlan + shift;

            if (orderByPlan > orderByProject){
                task.setValid(false);
                String res = String.format("Ошибка валидации! Порядок задачи '%s' неправильный!", task.getType());
                plan.getValidationResult().add(
                        new Plan.ValidationResult(Plan.ResultType.NOT_VALID, res)
                );
            }

            if (!(presentedByPlan || providedByRule) && requiredByRule){
                task.setValid(false);
                String res = String.format("Ошибка валидации! Задача '%s' отсутствует!", task.getType());
                plan.getValidationResult().add(
                        new Plan.ValidationResult(Plan.ResultType.NOT_VALID, res)
                );
            }
            if (presentedByPlan && providedByRule){
                task.setValid(false);
                String res = String.format("Ошибка валидации! Задача '%s' дублируется!", task.getType());
                plan.getValidationResult().add(
                        new Plan.ValidationResult(Plan.ResultType.NOT_VALID, res)
                );
            }
            if (task.getStatus() != null)
                plan.getValidationResult().add(new Plan.ValidationResult(Plan.ResultType.STATUS, task.getStatus()));
        }
    }
    public Plan validatePlan(Plan plan){

        validateTasks(plan);

        boolean result = plan.getTasks()
                .stream()
                .allMatch(Task::isValid);

        if (result)
            plan.getValidationResult().add(
                    new Plan.ValidationResult(
                            Plan.ResultType.VALID, "План валиден!"
                    )
            );
        else
            plan.getValidationResult().add(
                    new Plan.ValidationResult(
                            Plan.ResultType.NOT_VALID, "План невалиден!"
                    )
            );
        return plan;
    }
}