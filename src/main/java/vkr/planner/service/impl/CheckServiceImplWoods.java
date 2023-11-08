package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.PlanValidationRule;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.woods.TechnicalDescriptionWoods;
import vkr.planner.convert.WoodsModelConvert;
import vkr.planner.model.woods.WoodsRuleSet;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RuleTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

@Component
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String REQUEST_TYPE = "Леса";
    @Autowired
    private final WoodsModelConvert woodsModelConvert;
    @Autowired
    private final RuleTypeMapper ruleTypeMapper;
    public CheckServiceImplWoods(WoodsModelConvert woodsModelConvert, RuleTypeMapper ruleTypeMapper){
        this.woodsModelConvert = woodsModelConvert;
        this.ruleTypeMapper = ruleTypeMapper;
    }
    @Override
    public boolean isPlanValidByRule(Plan plan, @NotNull PlanValidationRule planValidationRule) {
        return true;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException {
        TechnicalDescriptionWoods technicalDescriptionWoods =
                convertToModel(checkRequest.getRequestFile().getInputStream(),
                new TechnicalDescriptionWoods());
        WoodsRuleSet woodsRuleSet = JsonUtils.parseJsonToObject(checkRequest.getRequestRules(), WoodsRuleSet.class);
        return JsonUtils.getJsonKeys(checkRequest.getRequestRules()).stream()
                .map(requestType -> {
                    try {
                        return ruleTypeMapper.getCheckServiceByRequestType(requestType);
                    } catch (UnknownTypeException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(e -> e.checkByRule(woodsRuleSet, technicalDescriptionWoods))
                .collect(Collectors.joining());
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
    }
    @Override
    public TechnicalDescriptionWoods convertToModel(InputStream inputStream, TechnicalDescriptionWoods obj) throws IOException, InvalidFormatException {
        return woodsModelConvert.convertMapToTechnicalDescriptionWoods(
                ExcelUtils.parseExcelFromInputStreamToMap(inputStream)
        );
    }
}
