package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.convert.PlanBuilder;
import vkr.planner.convert.RulesModelBuilder;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.RuleType;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.tea.RulesModelTea;
import vkr.planner.model.tea.TeaRuleSet;
import vkr.planner.model.tea.Teapot;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
@Component
public class CheckServiceImplTea implements CheckService<Teapot> {
    public static final String REQUEST_TYPE = "Чайник";
    @Autowired
    public final PlanBuilder planBuilder;
    public final RulesModelBuilder rulesModelBuilder;
    public Teapot teapot;
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;

    public CheckServiceImplTea(PlanBuilder planBuilder, RulesModelBuilder rulesModelBuilder, RuleTypeMapper ruleTypeMapper) {
        this.planBuilder = planBuilder;
        this.rulesModelBuilder = rulesModelBuilder;
        this.ruleTypeMapper = ruleTypeMapper;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, ConvertToDtoException, UnknownTypeException {
        Plan plan = planBuilder.convertMapToPlan(ExcelUtils.parseExcelFromInputStreamToMap(
                checkRequest.getRequestFile().getInputStream()
        ));
        TeaRuleSet teaRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(),
                TeaRuleSet.class);
        RulesModelTea rulesModelTea = rulesModelBuilder.build(teaRuleSet, plan);
        teapot = new Teapot(500.0, 100);

        implementRules(rulesModelTea);
        return getResult();
    }
    public void implementRules(RulesModelTea rulesModel) throws UnknownTypeException {
        for (RuleType ruleType: rulesModel.getRuleTypes()){
             teapot = (Teapot) ruleTypeMapper.getRulesCheckServiceByRuleType(ruleType)
                    .checkByRule(rulesModel, teapot);
        }
    }
    public String getResult(){
        return String.join("\n", teapot.getRuleTypeMapResult().values());
    }

    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
    }
}
