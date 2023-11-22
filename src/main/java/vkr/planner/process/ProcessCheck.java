package vkr.planner.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;
import vkr.planner.service.CheckService;
import vkr.planner.service.mapper.RequestTypeMapper;

@Component
public class ProcessCheck {
    private static final Logger logger = LogManager.getLogger(ProcessCheck.class);

    private final RequestTypeMapper requestTypeMapper;

    public ProcessCheck(RequestTypeMapper requestTypeMapper) {
        this.requestTypeMapper = requestTypeMapper;
    }

    public void process(CheckRequest checkRequest) throws UnknownTypeException {
        CheckService checkService = requestTypeMapper.getCheckServiceByRequestType(ObjectType.getEnum(checkRequest.getObjectType().toUpperCase()));
        String result;
        try {
            result = checkService.check(checkRequest);
        }
        catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        checkRequest.setResult(result);
        checkRequest.setResultType(CheckRequest.ResultType.SUCCESS);
        logger.info(">>> Результат проверки для запроса типа {} : \n" +
                ">>> {}", checkRequest.getObjectType(), result);
    }
}
