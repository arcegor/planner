package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.woods.CheckPlanWoods;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.TechnicalDescriptionWoodsBuilder;
import vkr.planner.model.woods.WoodsRuleSet;
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
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String NO_RULES = "Не передано никаких правил";

    @Autowired
    private final TechnicalDescriptionWoodsBuilder technicalDescriptionWoodsBuilder;
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final PlanBuilderMapper planBuilderMapper;
    @Autowired
    private final ProjectBuilder projectBuilder;
    private CheckPlanBuilder checkPlanBuilder;
    public TechnicalDescriptionWoods technicalDescriptionWoods; // Техническое описание объекта
    public Project project; // План-график
    public CheckServiceImplWoods(TechnicalDescriptionWoodsBuilder technicalDescriptionWoodsBuilder, RuleTypeMapper ruleTypeMapper, PlanBuilderMapper planBuilderMapper, ProjectBuilder projectBuilder, CheckPlanBuilder checkPlanBuilder){
        this.technicalDescriptionWoodsBuilder = technicalDescriptionWoodsBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.planBuilderMapper = planBuilderMapper;
        this.projectBuilder = projectBuilder;
        this.checkPlanBuilder = checkPlanBuilder;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap =
                ZipUtils.unzip(checkRequest.getRequestFile().getInputStream().readAllBytes());
        technicalDescriptionWoods =
                convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                        new TechnicalDescriptionWoods());
        WoodsRuleSet woodsRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(), WoodsRuleSet.class);
        project = projectBuilder.convertMapToProject(ExcelUtils.parseExcelFromInputStreamToMap(stringInputStreamMap.get(PROJECT)));
        checkPlanBuilder = getCheckPlanBuilder(project);
        CheckPlanWoods checkPlanWoods = (CheckPlanWoods) checkPlanBuilder.build(woodsRuleSet, project);
        if (checkPlanWoods.getIsEmpty())
            return NO_RULES;
        implementRules(checkPlanWoods);
        return getResult();
    }
    @Override
    public ObjectType getObjectType() {
        return ObjectType.РЕАКТОР;
    }
    @Override
    public TechnicalDescriptionWoods convertToModel(InputStream inputStream, TechnicalDescriptionWoods obj) throws IOException, InvalidFormatException {
        return technicalDescriptionWoodsBuilder.convertMapToTechnicalDescriptionWoods(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public String getResult(){
        return String.join("\n", technicalDescriptionWoods.getRuleTypeResult().values());
    }
    public void implementRules(CheckPlanWoods checkPlanWoods) throws UnknownTypeException {
        for (RuleType ruleType: checkPlanWoods.getRuleTypes()){
            technicalDescriptionWoods = (TechnicalDescriptionWoods) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(checkPlanWoods, technicalDescriptionWoods);
        }
    }
    public CheckPlanBuilder getCheckPlanBuilder(Project project) throws UnknownTypeException {
        return planBuilderMapper.getCheckPlanBuilderByPlanType(project.getProjectType());
    }
}
