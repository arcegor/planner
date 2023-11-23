package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.ProjectType;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckPlanTea;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.model.woods.CheckPlanWoods;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.TechnicalDescriptionWoodsBuilder;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckPlanBuilder;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.service.mapper.PlanBuilderMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;
import vkr.planner.utils.JsonUtils;
import vkr.planner.utils.ZipUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static vkr.planner.model.schedule.Project.PROJECT;
import static vkr.planner.model.schedule.Project.TECHNICAL_DESCRIPTION;

@Component
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String NO_RULES = "Не передано никаких правил";

    private final TechnicalDescriptionWoodsBuilder technicalDescriptionWoodsBuilder;
    private final RuleTypeMapper ruleTypeMapper;
    private final PlanBuilderMapper planBuilderMapper;
    private final ProjectBuilder projectBuilder;
    public CheckServiceImplWoods(TechnicalDescriptionWoodsBuilder technicalDescriptionWoodsBuilder, RuleTypeMapper ruleTypeMapper, PlanBuilderMapper planBuilderMapper, ProjectBuilder projectBuilder){
        this.technicalDescriptionWoodsBuilder = technicalDescriptionWoodsBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.planBuilderMapper = planBuilderMapper;
        this.projectBuilder = projectBuilder;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap = FileUtils.getInput(checkRequest.getRequestFile());
        Project project = projectBuilder.convertMapToProject(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get(PROJECT)
        ));
        WoodsRuleSet woodsRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(), WoodsRuleSet.class);
        project.setProjectType(ProjectType.ГЕРМЕТИЗАЦИЯ_ТРУБЫ);
        project.setPlan((CheckPlanWoods) getCheckPlanBuilder(project).build(woodsRuleSet, project));
        project.setTechnicalDescription(convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                new TechnicalDescriptionWoods()));
        if (project.getPlan().getIsEmpty())
            return NO_RULES;

        implementRules(project);

        return getResult(project);
    }
    @Override
    public ProjectType getProjectType() {
        return ProjectType.ГЕРМЕТИЗАЦИЯ_ТРУБЫ;
    }
    @Override
    public TechnicalDescriptionWoods convertToModel(InputStream inputStream, TechnicalDescriptionWoods obj) throws IOException, InvalidFormatException {
        return technicalDescriptionWoodsBuilder.convertMapToTechnicalDescriptionWoods(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public String getResult(Project project){
        return String.join("\n", project.getPlan().getRuleTypeResult().values());
    }
    public void implementRules(Project project) throws UnknownTypeException {
        for (RuleType ruleType: project.getPlan().getRuleTypes()){
            project.setPlan((CheckPlanWoods) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(project.getPlan(), project.getTechnicalDescription()));
        }
    }
    public CheckPlanBuilder getCheckPlanBuilder(Project project) throws UnknownTypeException {
        return planBuilderMapper.getCheckPlanBuilderByPlanType(project.getProjectType());
    }
}
