package vkr.planner;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.mapper.RuleTypeMapper;
import vkr.planner.model.Request;
import vkr.planner.repository.ProjectRepository;
import vkr.planner.service.ValidationService;
import vkr.planner.service.impl.ValidationServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ValidationRequestTest {
    private static final Logger logger = LogManager.getLogger(ValidationRequestTest.class);
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private RuleTypeMapper ruleTypeMapper;

    @Test
    void validateTest() throws IOException, InvalidFormatException, UnknownTypeException {
        String projectType = "Заваривание чая";
        File file1 = new File("src/main/resources/files/p.xlsx");
        File file2 = new File("src/main/resources/files/tc.xlsx");
        final InputStream inputStream1 = FileUtils.openInputStream(file1);
        final InputStream inputStream2 = FileUtils.openInputStream(file2);
        MultipartFile multipartFile1 = new MockMultipartFile("p", inputStream1);
        MultipartFile multipartFile2 = new MockMultipartFile("tc", inputStream2);

        MultipartFile[] multipartFiles = {multipartFile1, multipartFile2};

        Request request = new Request(projectType);
        request.setRequestFile(multipartFiles);
        ValidationService validationService = new ValidationServiceImpl(ruleTypeMapper, projectRepository);
        validationService.validateProject(request);
    }
}
