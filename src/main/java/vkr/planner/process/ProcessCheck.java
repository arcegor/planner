package vkr.planner.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.service.CheckProjectService;

import java.io.IOException;

@Component
public class ProcessCheck {
    private static final Logger logger = LogManager.getLogger(ProcessCheck.class);

    @Autowired
    private CheckProjectService checkProjectService;
    public void process(CheckRequest checkRequest) {
        String result;
        try {
            result = checkProjectService.check(checkRequest);
        }
        catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        checkRequest.setResult(result);
        checkRequest.setResultType(CheckRequest.ResultType.VALID);
        logger.info(">>> Результат проверки для проекта типа {} : \n" +
                ">>> {}", checkRequest.getProjectType(), result);
    }
}
