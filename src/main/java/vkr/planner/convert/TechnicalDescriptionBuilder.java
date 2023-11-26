package vkr.planner.convert;

import lombok.Data;
import org.springframework.stereotype.Service;
import vkr.planner.model.schedule.TechnicalDescription;

import java.util.List;
import java.util.Map;

@Service
public interface TechnicalDescriptionBuilder {
    public TechnicalDescription convertMapToTechnicalDescription(Map<Integer, List<String>> excelTable);

    String getBuilderType();
}
