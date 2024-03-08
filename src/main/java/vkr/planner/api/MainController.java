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
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.Request;
import vkr.planner.service.ValidationService;

import java.io.IOException;


@RestController
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);
    @Autowired
    private ValidationService validationService;
    @RequestMapping(value = "/request", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> validateRequest(
            @ModelAttribute Request request) throws UnknownTypeException, IOException, InvalidFormatException {

        logger.info(">>> Получен запрос с параметрами {}", request.toString());
        if (request.getRequestFile() == null || request.getProjectType() == null){
            logger.info(">>> Тело запроса пустое! {}", request.toString());
            return new ResponseEntity<>(request.toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            String result = validationService.validateProject(request).getValidationResult()
                    .toString();
        }
        catch (Exception exception){
            logger.error(exception.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
