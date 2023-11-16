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
public class TeaModelBuilder {
    private Map<Integer, List<String>> excelTable;
    public static Integer КОЛИЧЕСТВО_МЯТЫ = 1;
    public static Integer ТЕМПЕРАТУРА_ВОДЫ = 2;
    public static Integer ОБЪЕМ_ЧАЙНИКА = 3;

    public TechnicalDescriptionTea convertMapToTechnicalDescriptionTea(Map<Integer, List<String>> excelTable){
        TechnicalDescriptionTea technicalDescriptionTea = new TechnicalDescriptionTea();
        this.excelTable = excelTable;

        try {
            for (Integer key: excelTable.keySet()){

                technicalDescriptionTea.setTemp(Integer.parseInt(excelTable.get(key).get(ТЕМПЕРАТУРА_ВОДЫ)));
                technicalDescriptionTea.setVolume(Integer.parseInt(excelTable.get(key).get(КОЛИЧЕСТВО_МЯТЫ)));
                technicalDescriptionTea.setMintCount(Integer.parseInt(excelTable.get(key).get(КОЛИЧЕСТВО_МЯТЫ)));
            }
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return technicalDescriptionTea;
    }
}


