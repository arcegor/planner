package vkr.planner;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;
import vkr.planner.api.MainController;
import vkr.planner.model.db.Condition;
import vkr.planner.model.db.Task;
import vkr.planner.utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationRequestTest {
    private static final Logger logger = LogManager.getLogger(ValidationRequestTest.class);

    @Test
    void parseExcelFilesTest() throws IOException, InvalidFormatException {
        File file1 = new File("src/main/resources/files/p.xlsx");
        File file2 = new File("src/main/resources/files/td.xlsx");
        final InputStream inputStream1 = FileUtils.openInputStream(file1);
        final InputStream inputStream2 = FileUtils.openInputStream(file2);
        List<Condition> conditions = ExcelUtils.getObjectsListFromExcelFile(
                        inputStream2, Condition.class);
        List<Task> tasks = ExcelUtils.getObjectsListFromExcelFile(
                inputStream1, Task.class);
        logger.info(conditions.stream()
                .map(Condition::getType)
                .collect(Collectors.joining(", ")));
        logger.info(tasks.stream()
                .map(Task::getType)
                .collect(Collectors.joining(", ")));
    }
}
