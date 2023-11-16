package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.convert.TeaModelBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.tea.CheckScenarioTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.model.tea.TechnicalDescriptionTea;
import vkr.planner.service.CheckScenarioBuilder;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.service.mapper.ScenarioBuilderMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;
import vkr.planner.utils.ZipUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static vkr.planner.model.schedule.Plan.PLAN;
import static vkr.planner.model.schedule.Plan.TECHNICAL_DESCRIPTION;
import static vkr.planner.service.impl.CheckServiceImplWoods.NO_RULES;

@Component
public class CheckServiceImplTea implements CheckService<TechnicalDescriptionTea> {
    public final PlanBuilder planBuilder;
    @Autowired
    private final ScenarioBuilderMapper scenarioBuilderMapper;
    public TechnicalDescriptionTea technicalDescriptionTea;
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final TeaModelBuilder teaModelBuilder;


    public CheckServiceImplTea(PlanBuilder planBuilder, ScenarioBuilderMapper scenarioBuilderMapper, RuleTypeMapper ruleTypeMapper, TeaModelBuilder teaModelBuilder) {
        this.planBuilder = planBuilder;
        this.scenarioBuilderMapper = scenarioBuilderMapper;
        this.ruleTypeMapper = ruleTypeMapper;
        this.teaModelBuilder = teaModelBuilder;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap =
                ZipUtils.unzip(checkRequest.getRequestFile().getInputStream().readAllBytes());
        Plan plan = planBuilder.convertMapToPlan(ExcelUtils.parseExcelFromInputStreamToMap(
                stringInputStreamMap.get(PLAN)
        ));
        TeaRuleSet teaRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(),
                TeaRuleSet.class);
        technicalDescriptionTea = convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                new TechnicalDescriptionTea());
        CheckScenarioBuilder checkScenarioBuilder = getCheckScenarioBuilder(plan);
        CheckScenarioTea checkScenarioTea = (CheckScenarioTea) checkScenarioBuilder.build(teaRuleSet, plan);

        if (checkScenarioTea.isEmpty())
            return NO_RULES;

        implementRules(checkScenarioTea);

        return getResult();
    }
    @Override
    public TechnicalDescriptionTea convertToModel(InputStream inputStream, TechnicalDescriptionTea obj) throws IOException, InvalidFormatException {
        return teaModelBuilder.convertMapToTechnicalDescriptionTea(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public void implementRules(CheckScenarioTea rulesModel) throws UnknownTypeException {
        for (RuleType ruleType: rulesModel.getRuleTypes()){
             technicalDescriptionTea = (TechnicalDescriptionTea) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(rulesModel, technicalDescriptionTea);
        }
    }
    public String getResult(){
        return String.join("\n", technicalDescriptionTea.getRuleTypeMapResult().values());
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.КУХНЯ;
    }
    public CheckScenarioBuilder getCheckScenarioBuilder(Plan plan) throws UnknownTypeException {
        return scenarioBuilderMapper.getCheckScenarioBuilderByPlanType(plan.getPlanType());
    }

}
