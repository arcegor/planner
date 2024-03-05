package vkr.planner.utils;


import com.poiji.bind.Poiji;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ExcelUtils {
    public static <T> List<T> getObjectsListFromExcelFile(InputStream inputStream, Class<T> clazz, Integer sheetIndex) throws IOException, InvalidFormatException {
        OPCPackage pkg = OPCPackage.open(inputStream);
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        return Poiji.fromExcel(sheet, clazz);
    }
    public static <T> List<T> getObjectsListFromExcelFile(InputStream inputStream, Class<T> clazz) throws IOException, InvalidFormatException {
        OPCPackage pkg = OPCPackage.open(inputStream);
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        Sheet sheet = workbook.getSheetAt(0);
        return Poiji.fromExcel(sheet, clazz);
    }
}
