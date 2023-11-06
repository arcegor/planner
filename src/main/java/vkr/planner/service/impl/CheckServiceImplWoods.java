package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.PlanValidationRule;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.specification.woods.TechnicalDescriptionWoods;
import vkr.planner.process.WoodsModelConvert;
import vkr.planner.service.CheckService;
import vkr.planner.utils.ExcelUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class CheckServiceImplWoods implements CheckService<TechnicalDescriptionWoods> {
    public static final String REQUEST_TYPE = "Леса";
    @Autowired
    private final WoodsModelConvert woodsModelConvert;
    public CheckServiceImplWoods(WoodsModelConvert woodsModelConvert){
        this.woodsModelConvert = woodsModelConvert;
    }

    @Override
    public boolean isPlanValidByRule(Plan plan, @NotNull PlanValidationRule planValidationRule) {
        return true;
    }
    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException {
        TechnicalDescriptionWoods technicalDescriptionWoods = convertToModel(checkRequest, new TechnicalDescriptionWoods());
        checkRequest.setResultType(CheckRequest.ResultType.SUCCESS);
        return checkRequest.getRequestType();
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
    }
    @Override
    public TechnicalDescriptionWoods convertToModel(CheckRequest checkRequest, TechnicalDescriptionWoods obj) throws IOException, InvalidFormatException {
        Map<Integer, List<String>> excelTable = ExcelUtils.parseExcelFromInputStreamToMap(
                checkRequest.getRequestFile().getInputStream());
        woodsModelConvert.setExcelTable(excelTable);
        return woodsModelConvert.convertMapToTechnicalDescriptionWoods();
    }
}
