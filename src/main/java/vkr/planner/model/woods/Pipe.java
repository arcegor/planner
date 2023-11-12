package vkr.planner.model.woods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pipe {
    private int id;
    private double x, y, z;
    private boolean isNeedToBeThermallyTnsulated;
    private String kks;
    private List<String> neighbouringAreas;
    private double level;
    private AreaType areaType;
    @Override
    public String toString(){
        return "Проходка(Порядковый номер: " + id + ", координаты: " +
                x + ", " + y + ", " + z + ", должна быть теплоизолирована: " + iso() +
                ", код ККС: " + kks + ", тип помещения: " + areaType + ", уровень пола в помещении: " + level +
                ", смежные помещения: " + neighbouringAreasToString() + ")";
    }

    public String iso(){
        return isNeedToBeThermallyTnsulated ? "да" : "нет";
    }
    public String neighbouringAreasToString(){
        return String.join(", ", neighbouringAreas);
    }
}
