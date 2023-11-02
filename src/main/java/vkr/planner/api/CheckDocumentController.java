package vkr.planner.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.service.CheckService;
import vkr.planner.service.RequestTypeMapper;


@RestController
public class CheckDocumentController {
    private static final Logger logger = LogManager.getLogger(CheckDocumentController.class);
    @Autowired
    private RequestTypeMapper requestTypeMapper;

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(
            @ModelAttribute CheckRequest checkRequest) throws UnknownTypeException {

        if (checkRequest.getRequestFile() == null || checkRequest.getRequestType() == null){
            return new ResponseEntity<>(checkRequest.toString(), HttpStatus.BAD_REQUEST);
        }
        CheckService checkService = requestTypeMapper.getCheckServiceByRequestType(checkRequest.getRequestType());
        String result = checkService.check();
        logger.info(">>> Результат проверки для запроса типа {} : " +
                "{}", checkRequest.getRequestType(), result);
        return new ResponseEntity<>(checkRequest.toString(), HttpStatus.OK);
    }
}
