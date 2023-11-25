package vkr.planner.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.service.CheckProjectService;
import vkr.planner.service.mapper.ProjectTypeMapper;

@Component
public class ProcessCheck {
    private static final Logger logger = LogManager.getLogger(ProcessCheck.class);

    private final ProjectTypeMapper projectTypeMapper;

    public ProcessCheck(ProjectTypeMapper projectTypeMapper) {
        this.projectTypeMapper = projectTypeMapper;
    }

    public void process(CheckRequest checkRequest) throws UnknownTypeException {
        CheckProjectService checkProjectService = projectTypeMapper.getCheckServiceByProjectType(checkRequest.getProjectType());
        String result;
        try {
            result = checkProjectService.check(checkRequest);
        }
        catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        checkRequest.setResult(result);
        checkRequest.setResultType(CheckRequest.ResultType.SUCCESS);
        logger.info(">>> Результат проверки для запроса типа {} : \n" +
                ">>> {}", checkRequest.getProjectType(), result);
    }
}
