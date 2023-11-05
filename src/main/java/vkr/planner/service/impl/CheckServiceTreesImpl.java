package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.Rule;
import vkr.planner.model.type2.Plan;
import vkr.planner.process.ForestAseBuild;
import vkr.planner.service.CheckService;
import vkr.planner.utils.ExcelUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class CheckServiceTreesImpl implements CheckService {
    public static final String REQUEST_TYPE = "Леса";
    @Autowired
    private final ForestAseBuild forestAseBuild;
    public CheckServiceTreesImpl(ForestAseBuild forestAseBuild){
        this.forestAseBuild = forestAseBuild;
    }
    @Override
    public boolean checkPlanByRule(Plan plan, @NotNull Rule rule) {
        return false;
    }

    @Override
    public String check(CheckRequest checkRequest) throws IOException, InvalidFormatException {
        Map<Integer, List<String>> excelTable = ExcelUtils.parseExcelFromInputStreamToMap(
                    checkRequest.getRequestFile().getInputStream());

        forestAseBuild.setExcelTable(excelTable);
        forestAseBuild.getAreas();
        checkRequest.setResultType(CheckRequest.ResultType.SUCCESS);
        return forestAseBuild.createResponse();
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
    }
}
