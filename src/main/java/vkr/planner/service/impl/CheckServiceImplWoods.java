package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.convert.RulesModelBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.woods.RuleType;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.WoodsModelBuilder;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;
import vkr.planner.utils.ZipUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String REQUEST_TYPE = "Леса";
    public static final String NO_RULES = "Не передано никаких правил";
    public static final String PLAN = "План";
    public static final String TECHNICAL_DESCRIPTION = "Техническое описание";

    @Autowired
    private final WoodsModelBuilder woodsModelBuilder;
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final RulesModelBuilder rulesModelBuilder;
    @Autowired
    private final PlanBuilder planBuilder;
    public TechnicalDescriptionWoods technicalDescriptionWoods; // Техническое описание объекта
    public Plan plan; // План-график
    public CheckServiceImplWoods(WoodsModelBuilder woodsModelBuilder, RuleTypeMapper ruleTypeMapper, RulesModelBuilder rulesModelBuilder, PlanBuilder planBuilder){
        this.woodsModelBuilder = woodsModelBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.rulesModelBuilder = rulesModelBuilder;
        this.planBuilder = planBuilder;
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

        RulesModel rulesModel = rulesModelBuilder.build(woodsRuleSet, plan);

        if (rulesModel.getIsEmpty())
            return NO_RULES;

        implementRules(rulesModel);
        return getResult();
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
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
    public void implementRules(RulesModel rulesModel) throws UnknownTypeException {
        for (RuleType ruleType: rulesModel.getRuleTypes()){
            technicalDescriptionWoods = (TechnicalDescriptionWoods) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(rulesModel, technicalDescriptionWoods);
        }
    }
}
