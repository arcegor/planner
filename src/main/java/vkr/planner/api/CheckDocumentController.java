package vkr.planner.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.process.ProcessCheck;

import java.io.IOException;


@RestController
public class CheckDocumentController {
    private static final Logger logger = LogManager.getLogger(CheckDocumentController.class);
    @Autowired
    private ProcessCheck processCheck;

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(
            @ModelAttribute CheckRequest checkRequest) throws UnknownTypeException {

        logger.info(">>> Получен запрос с параметрами {}", checkRequest.toString());
        if (checkRequest.getRequestFile() == null || checkRequest.getRequestType() == null){
            logger.info(">>> Тело запроса пустое! {}", checkRequest.toString());
            return new ResponseEntity<>(checkRequest.toString(), HttpStatus.BAD_REQUEST);
        }
        processCheck.process(checkRequest);
        return new ResponseEntity<>(checkRequest.toString(), HttpStatus.OK);
    }
}
