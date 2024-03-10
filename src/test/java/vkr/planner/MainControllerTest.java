package vkr.planner;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import vkr.planner.api.MainController;
import vkr.planner.service.ValidationService;

import java.io.File;
import java.io.InputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ValidationService validationService;
    @Test
    void uploadRequestTest() throws Exception {
        File file1 = new File("src/main/resources/files/p.xlsx");
        File file2 = new File("src/main/resources/files/tc.xlsx");
        final InputStream inputStream1 = FileUtils.openInputStream(file1);
        final InputStream inputStream2 = FileUtils.openInputStream(file2);
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("requestFile",
                "p.xlsx","multipart/form-data", inputStream1
        );
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("requestFile",
                "tc.xlsx", "multipart/form-data", inputStream2
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/request")
                        .file(mockMultipartFile1)
                        .file(mockMultipartFile2)
                        .param("projectType", "123"))
                .andExpect(status().is(200));
    }
}
