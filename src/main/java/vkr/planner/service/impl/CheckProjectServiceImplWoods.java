package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Component;
import vkr.planner.convert.ProjectBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Project;
import vkr.planner.model.woods.PlanWoods;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.TechnicalDescriptionWoodsBuilder;
import vkr.planner.service.CheckProjectService;
import vkr.planner.service.impl.plans.PlanWoodsBuilder;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.FileUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class CheckProjectServiceImplWoods implements CheckProjectService {
    public static final String NO_RULES = "Не передано никаких правил";
    public static final String PROJECT_TYPE = "Герметизация труб";
    private final TechnicalDescriptionWoodsBuilder technicalDescriptionWoodsBuilder;
    private final RuleTypeMapper ruleTypeMapper;
    private final ProjectBuilder projectBuilder;
    private final PlanWoodsBuilder planWoodsBuilder;
    public CheckProjectServiceImplWoods(TechnicalDescriptionWoodsBuilder technicalDescriptionWoodsBuilder, RuleTypeMapper ruleTypeMapper, ProjectBuilder projectBuilder, PlanWoodsBuilder planWoodsBuilder){
        this.technicalDescriptionWoodsBuilder = technicalDescriptionWoodsBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.projectBuilder = projectBuilder;
        this.planWoodsBuilder = planWoodsBuilder;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap = FileUtils.getInput(checkRequest.getRequestFile());
        Project project = projectBuilder.convertMapToProject(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get()
        ));
        WoodsRuleSet woodsRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(), WoodsRuleSet.class);
        project.setProjectType(PROJECT_TYPE);
        project.setPlan(planWoodsBuilder.build(woodsRuleSet, project));
        project.setTechnicalDescription(convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                new TechnicalDescriptionWoods()));
        if (project.getPlan().getIsEmpty())
            return NO_RULES;

        implementRules(project);

        return getResult(project);
    }
    @Override
    public String getProjectType() {
        return PROJECT_TYPE;
    }
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
            project.setPlan((PlanWoods) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(project.getPlan(), project.getTechnicalDescription()));
        }
    }
}
