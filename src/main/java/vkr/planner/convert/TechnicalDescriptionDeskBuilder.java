package vkr.planner.convert;

import org.springframework.stereotype.Component;
import vkr.planner.model.desk.TechnicalDescriptionDesk;
import vkr.planner.model.schedule.TechnicalDescription;
import vkr.planner.model.tea.TechnicalDescriptionTea;

import java.util.List;
import java.util.Map;
@Component
public class TechnicalDescriptionDeskBuilder implements TechnicalDescriptionBuilder{
    public static final String PROJECT_TYPE = "Гладильная доска";
    private Map<Integer, List<String>> excelTable;
    public static Integer КОЛИЧЕСТВО_ВЕЩЕЙ = 1;
    public static Integer ТЕМПЕРАТУРА_УТЮГА = 0;
    @Override
    public TechnicalDescription convertMapToTechnicalDescription(Map<Integer, List<String>> excelTable) {
        TechnicalDescriptionDesk technicalDescriptionDesk = new TechnicalDescriptionDesk();
        this.excelTable = excelTable;

        try {
            int key = 1;
            technicalDescriptionDesk.setTemp((int) Double.parseDouble(excelTable.get(key).get(ТЕМПЕРАТУРА_УТЮГА)));
            technicalDescriptionDesk.setClothes((int) Double.parseDouble(excelTable.get(key).get(КОЛИЧЕСТВО_ВЕЩЕЙ)));
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return technicalDescriptionDesk;
    }

    @Override
    public String getBuilderType() {
        return PROJECT_TYPE;
    }
}
