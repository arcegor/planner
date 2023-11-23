package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Component;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.convert.TechnicalDescriptionTeaBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.ProjectType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckPlanTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckPlanBuilder;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.PlanBuilderMapper;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static vkr.planner.model.schedule.Project.PROJECT;
import static vkr.planner.model.schedule.Project.TECHNICAL_DESCRIPTION;


@Component
public class CheckServiceImplTea implements CheckService<TechnicalDescriptionTea> {
    public static final String NO_RULES = "Не передано никаких правил";

    public final ProjectBuilder projectBuilder;
    private final PlanBuilderMapper planBuilderMapper;
    private final RuleTypeMapper ruleTypeMapper;
    private final TechnicalDescriptionTeaBuilder technicalDescriptionTeaBuilder;


    public CheckServiceImplTea(ProjectBuilder projectBuilder, PlanBuilderMapper planBuilderMapper, RuleTypeMapper ruleTypeMapper, TechnicalDescriptionTeaBuilder technicalDescriptionTeaBuilder) {
        this.projectBuilder = projectBuilder;
        this.planBuilderMapper = planBuilderMapper;
        this.ruleTypeMapper = ruleTypeMapper;
        this.technicalDescriptionTeaBuilder = technicalDescriptionTeaBuilder;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap = FileUtils.getInput(checkRequest.getRequestFile());
        Project project = projectBuilder.convertMapToProject(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get(PROJECT)
        ));
        TeaRuleSet teaRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(),
                TeaRuleSet.class);
        project.setProjectType(ProjectType.ЗАВАРИВАНИЕ_ЧАЯ);
        project.setPlan((CheckPlanTea) getCheckPlanBuilder(project).build(teaRuleSet, project));
        project.setTechnicalDescription(convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                new TechnicalDescriptionTea()));
        if (project.getPlan().getIsEmpty())
            return NO_RULES;

        implementRules(project);

        return getResult(project);
    }
    @Override
    public TechnicalDescriptionTea convertToModel(InputStream inputStream, TechnicalDescriptionTea obj) throws IOException, InvalidFormatException {
        return technicalDescriptionTeaBuilder.convertMapToTechnicalDescriptionTea(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public void implementRules(Project project) throws UnknownTypeException {
        for (RuleType ruleType: project.getPlan().getRuleTypes()){
             project.setPlan((CheckPlanTea) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(project.getPlan(), project.getTechnicalDescription()));
        }
    }
    public String getResult(Project project){
        return String.join("\n", project.getPlan().getRuleTypeResult().values());
    }

    @Override
    public ProjectType getProjectType() {
        return ProjectType.ЗАВАРИВАНИЕ_ЧАЯ;
    }
    public CheckPlanBuilder getCheckPlanBuilder(Project project) throws UnknownTypeException {
        return planBuilderMapper.getCheckPlanBuilderByPlanType(project.getProjectType());
    }

}
