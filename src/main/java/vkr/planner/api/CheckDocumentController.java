package vkr.planner.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vkr.planner.model.CheckRequest;
import vkr.planner.service.CheckService;


@RestController
public class CheckDocumentController {

    private CheckService checkService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CheckRequest> uploadFile(
            @RequestParam @Nullable String requestType,
            @RequestParam @Nullable String requestData,
            @RequestParam(value = "requestFile") @Nullable MultipartFile file) {
        CheckRequest checkRequest = new CheckRequest(requestType, requestData);
        if (file == null || checkRequest.getRequestType() == null){
            return new ResponseEntity<>(checkRequest, HttpStatus.BAD_REQUEST);
        }
        checkRequest.setRequestFile(file);
        System.out.println(checkService.getInstance(checkRequest.getRequestType()).check());
        return new ResponseEntity<>(checkRequest, HttpStatus.OK);
    }
}
