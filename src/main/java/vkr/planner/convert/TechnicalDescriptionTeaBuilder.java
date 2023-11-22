package vkr.planner.convert;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import vkr.planner.model.tea.TechnicalDescriptionTea;

import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class TechnicalDescriptionTeaBuilder {
    private Map<Integer, List<String>> excelTable;
    public static Integer КОЛИЧЕСТВО_МЯТЫ = 0;
    public static Integer ТЕМПЕРАТУРА_ВОДЫ = 1;
    public static Integer ОБЪЕМ_ЧАЙНИКА = 2;

    public TechnicalDescriptionTea convertMapToTechnicalDescriptionTea(Map<Integer, List<String>> excelTable){
        TechnicalDescriptionTea technicalDescriptionTea = new TechnicalDescriptionTea();
        this.excelTable = excelTable;

        try {
            int key = 1;
            technicalDescriptionTea.setTemp((int) Double.parseDouble(excelTable.get(key).get(ТЕМПЕРАТУРА_ВОДЫ)));
            technicalDescriptionTea.setVolume((int) Double.parseDouble(excelTable.get(key).get(ОБЪЕМ_ЧАЙНИКА)));
            technicalDescriptionTea.setMintCount((int) Double.parseDouble(excelTable.get(key).get(КОЛИЧЕСТВО_МЯТЫ)));
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return technicalDescriptionTea;
    }
}


