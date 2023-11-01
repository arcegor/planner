package vkr.planner.utils;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import vkr.planner.model.DocumentDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtils {
    public static DocumentDto parseExcel(InputStream inputStream) throws IOException, InvalidFormatException {
        DocumentDto documentDto = new DocumentDto();
        OPCPackage pkg = OPCPackage.open(inputStream);
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add(cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    default: data.get(i).add(" ");
                }
            }
            i++;
        }
        documentDto.convertToDto(data);
        return documentDto;
    }
}
