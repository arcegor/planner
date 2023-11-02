package vkr.planner.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.DocumentDto;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;
import vkr.planner.service.CheckService;
import vkr.planner.utils.ExcelUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CheckServiceImpl2 implements CheckService<DocumentDto> {
    public static final String REQUEST_TYPE = "Второй";

    public static final String CHECK_RESPONSE_SUCCESS = "Проверка запроса пройдена!";

    public static final String CHECK_RESPONSE_FAILED = "Проверка запроса не пройдена!";

    @Override
    public boolean checkPlanByRule(Plan plan, @NotNull Rule rule) {
        return false;
    }
    @Override
    public String check(CheckRequest checkRequest) throws ConvertToDtoException {
        DocumentDto documentDto = new DocumentDto();
        try {
            documentDto.convertToDto(ExcelUtils.parseExcelFromInputStreamToMap(
                    checkRequest.getRequestFile().getInputStream()));
        }
        catch (Exception exception){
            throw new ConvertToDtoException(exception.getMessage());
        }
        if (Objects.equals(checkRequest.getRequestData(), "Успех"))
            return CHECK_RESPONSE_SUCCESS;
        else return CHECK_RESPONSE_FAILED;
    }
    @Override
    public String getRequestType() {
        return REQUEST_TYPE;
    }
    @Override
    public DocumentDto convertToModel(Map<Integer, List<String>> data, DocumentDto documentDto){
        documentDto.convertToDto(data);
        return documentDto;
    }
}
