package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.convert.TechnicalDescriptionTeaBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckPlanTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckPlanBuilder;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.service.mapper.PlanBuilderMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;
import vkr.planner.utils.ZipUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static vkr.planner.model.schedule.Project.PROJECT;
import static vkr.planner.model.schedule.Project.TECHNICAL_DESCRIPTION;


@Component
public class CheckServiceImplTea implements CheckService<TechnicalDescriptionTea> {
    public static final String NO_RULES = "Не передано никаких правил";

    public final ProjectBuilder projectBuilder;
    @Autowired
    private final PlanBuilderMapper planBuilderMapper;
    public TechnicalDescriptionTea technicalDescriptionTea;
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final TechnicalDescriptionTeaBuilder technicalDescriptionTeaBuilder;


    public CheckServiceImplTea(ProjectBuilder projectBuilder, PlanBuilderMapper planBuilderMapper, RuleTypeMapper ruleTypeMapper, TechnicalDescriptionTeaBuilder technicalDescriptionTeaBuilder) {
        this.projectBuilder = projectBuilder;
        this.planBuilderMapper = planBuilderMapper;
        this.ruleTypeMapper = ruleTypeMapper;
        this.technicalDescriptionTeaBuilder = technicalDescriptionTeaBuilder;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap =
                ZipUtils.unzip(checkRequest.getRequestFile().getInputStream().readAllBytes());
        Project project = projectBuilder.convertMapToProject(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get(PROJECT)
        ));
        TeaRuleSet teaRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(),
                TeaRuleSet.class);
        technicalDescriptionTea = convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                new TechnicalDescriptionTea());
        CheckPlanBuilder checkPlanBuilder = getCheckPlanBuilder(project);
        CheckPlanTea checkPlanTea = (CheckPlanTea) checkPlanBuilder.build(teaRuleSet, project);

        if (checkPlanTea.getIsEmpty())
            return NO_RULES;

        implementRules(checkPlanTea);

        return getResult();
    }
    @Override
    public TechnicalDescriptionTea convertToModel(InputStream inputStream, TechnicalDescriptionTea obj) throws IOException, InvalidFormatException {
        return technicalDescriptionTeaBuilder.convertMapToTechnicalDescriptionTea(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public void implementRules(CheckPlanTea checkPlanTea) throws UnknownTypeException {
        for (RuleType ruleType: checkPlanTea.getRuleTypes()){
             technicalDescriptionTea = (TechnicalDescriptionTea) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(checkPlanTea, technicalDescriptionTea);
        }
    }
    public String getResult(){
        return String.join("\n", technicalDescriptionTea.getRuleTypeMapResult().values());
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.КУХНЯ;
    }
    public CheckPlanBuilder getCheckPlanBuilder(Project project) throws UnknownTypeException {
        return planBuilderMapper.getCheckPlanBuilderByPlanType(project.getProjectType());
    }

}
