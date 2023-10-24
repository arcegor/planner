package vkr.planner.api;

import io.github.millij.poi.SpreadsheetReadException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vkr.planner.model.DocumentDto;
import vkr.planner.utils.ExcelUtils;

import java.io.IOException;


@RestController(value = "/request")
public class CheckDocumentController {

    @Autowired
    private ExcelUtils excelUtils;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "file") @Nullable MultipartFile file) throws IOException, SpreadsheetReadException, InvalidFormatException {
        if (file != null){
            DocumentDto documentDto = ExcelUtils.parseExcel(file.getInputStream());
            System.out.println(documentDto);
        }
        System.out.println(key);
    }

}
