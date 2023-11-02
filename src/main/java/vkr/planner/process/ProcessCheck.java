package vkr.planner.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RequestTypeMapper;
import vkr.planner.utils.ExcelUtils;
import vkr.planner.utils.JsonUtils;

import java.io.IOException;

@Component
public class ProcessCheck {
    private static final Logger logger = LogManager.getLogger(ProcessCheck.class);
    @Autowired
    private final RequestTypeMapper requestTypeMapper;
    @Autowired
    private final JsonUtils jsonUtils;
    @Autowired
    private final ExcelUtils excelUtils;

    public ProcessCheck(RequestTypeMapper requestTypeMapper, JsonUtils jsonUtils, ExcelUtils excelUtils) {
        this.requestTypeMapper = requestTypeMapper;
        this.jsonUtils = jsonUtils;
        this.excelUtils = excelUtils;
    }

    public void process(CheckRequest checkRequest) throws UnknownTypeException, IOException, InvalidFormatException, ConvertToDtoException {
        CheckService checkService = requestTypeMapper.getCheckServiceByRequestType(checkRequest.getRequestType());
        String result = checkService.check(checkRequest);
        logger.info(">>> Результат проверки для запроса типа {} : " +
                "{}", checkRequest.getRequestType(), result);
    }
}
