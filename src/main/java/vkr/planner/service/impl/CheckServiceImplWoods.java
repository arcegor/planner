package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.RuleType;
import vkr.planner.model.woods.CheckScenarioWoods;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.WoodsModelBuilder;
import vkr.planner.model.woods.WoodsRuleSet;
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

@Component
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String REQUEST_TYPE = "Леса";
    public static final String NO_RULES = "Не передано никаких правил";

    @Autowired
    private final WoodsModelBuilder woodsModelBuilder;
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final ScenarioBuilderMapper scenarioBuilderMapper;
    @Autowired
    private final PlanBuilder planBuilder;
    private CheckScenarioBuilder checkScenarioBuilder;
    public TechnicalDescriptionWoods technicalDescriptionWoods; // Техническое описание объекта
    public Plan plan; // План-график
    public CheckServiceImplWoods(WoodsModelBuilder woodsModelBuilder, RuleTypeMapper ruleTypeMapper, ScenarioBuilderMapper scenarioBuilderMapper, PlanBuilder planBuilder, CheckScenarioBuilder checkScenarioBuilder){
        this.woodsModelBuilder = woodsModelBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.scenarioBuilderMapper = scenarioBuilderMapper;
        this.planBuilder = planBuilder;
        this.checkScenarioBuilder = checkScenarioBuilder;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        Map<String, InputStream> stringInputStreamMap =
                ZipUtils.unzip(checkRequest.getRequestFile().getInputStream().readAllBytes());
        technicalDescriptionWoods =
                convertToModel(stringInputStreamMap.get(TECHNICAL_DESCRIPTION),
                        new TechnicalDescriptionWoods());
        WoodsRuleSet woodsRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(), WoodsRuleSet.class);
        plan = planBuilder.convertMapToPlan(ExcelUtils.parseExcelFromInputStreamToMap(stringInputStreamMap.get(PLAN)));
        checkScenarioBuilder = getCheckScenarioBuilder(plan);
        CheckScenarioWoods checkScenarioWoods = (CheckScenarioWoods) checkScenarioBuilder.build(woodsRuleSet, plan);
        if (checkScenarioWoods.getIsEmpty())
            return NO_RULES;
        implementRules(checkScenarioWoods);
        return getResult();
    }
    @Override
    public ObjectType getObjectType() {
        return ObjectType.РЕАКТОР;
    }
    @Override
    public TechnicalDescriptionWoods convertToModel(InputStream inputStream, TechnicalDescriptionWoods obj) throws IOException, InvalidFormatException {
        return woodsModelBuilder.convertMapToTechnicalDescriptionWoods(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
    public String getResult(){
        return String.join("\n", technicalDescriptionWoods.getRuleTypeResult().values());
    }
    public void implementRules(CheckScenarioWoods checkScenarioWoods) throws UnknownTypeException {
        for (RuleType ruleType: checkScenarioWoods.getRuleTypes()){
            technicalDescriptionWoods = (TechnicalDescriptionWoods) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(checkScenarioWoods, technicalDescriptionWoods);
        }
    }
    public CheckScenarioBuilder getCheckScenarioBuilder(Plan plan) throws UnknownTypeException {
        return scenarioBuilderMapper.getCheckScenarioBuilderByPlanType(plan.getPlanType());
    }
}
