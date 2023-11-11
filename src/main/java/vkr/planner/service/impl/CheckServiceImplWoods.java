package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.RulesModelBuilder;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.woods.RuleType;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.WoodsModelBuilder;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;

@Component
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String REQUEST_TYPE = "Леса";
    public static final String NO_RULES = "Не передано никаких правил";
    @Autowired
    private final WoodsModelBuilder woodsModelBuilder;
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;
    @Autowired
    private final RulesModelBuilder rulesModelBuilder;
    public TechnicalDescriptionWoods technicalDescriptionWoods; // Техническое описание объекта
    public CheckServiceImplWoods(WoodsModelBuilder woodsModelBuilder, RuleTypeMapper ruleTypeMapper, RulesModelBuilder rulesModelBuilder){
        this.woodsModelBuilder = woodsModelBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
        this.rulesModelBuilder = rulesModelBuilder;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException {
        technicalDescriptionWoods =
                convertToModel(checkRequest.getRequestFile().getInputStream(),
                        new TechnicalDescriptionWoods());
        WoodsRuleSet woodsRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(), WoodsRuleSet.class);
        RulesModel rulesModel = rulesModelBuilder.build(woodsRuleSet);

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
