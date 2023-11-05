package vkr.planner.process;

import com.google.common.collect.ImmutableMap;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import vkr.planner.model.woods.Area;
import vkr.planner.model.woods.Floor;
import vkr.planner.model.woods.Wall;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter
@Setter
public class ForestAseBuild {
    enum CellPatternEnumType{
        ТАБЛИЦА,
        ПЕРЕКРЫТИЕ,
        СТЕНА;
    }
    private Map<CellPatternEnumType, Pattern> cellParseMapping;
    private List<Area> areaList;
    private Map<Integer, List<String>> excelTable;
    @PostConstruct
    public void init(){
        cellParseMapping = ImmutableMap.<CellPatternEnumType, Pattern>builder()
                .put(CellPatternEnumType.СТЕНА, Pattern.compile(" *" + "с отметки" + "-\\d+,\\d+" + "\\+\\d+,\\d+"))
                .put(CellPatternEnumType.ПЕРЕКРЫТИЕ, Pattern.compile(" *" + "(на отметке|с отметки)" + "(-\\d+,\\d+" + "|" + "\\+\\d+,\\d+)"))
                .put(CellPatternEnumType.ТАБЛИЦА, Pattern.compile("таблицу"))
    .build();
    }
    public boolean isMatched(String cell, Pattern pattern){
        Matcher matcher = pattern.matcher(cell);
        return matcher.matches();
    }
    private Optional<CellPatternEnumType> parseCell(String cell){
        return cellParseMapping.entrySet().stream()
                .filter(e -> !cell.trim().isEmpty())
                .filter(e -> isMatched(cell, e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }
    private Optional<CellPatternEnumType> parseRow(List<String> row){
        return row.stream()
                .map(this::parseCell)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
    public void getAreas(){
        for (Integer key: excelTable.keySet()){
            Optional<CellPatternEnumType> cellPatternEnumType = parseRow(excelTable.get(key));
            if (cellPatternEnumType.isEmpty())
                continue;
            switch (cellPatternEnumType.get()){
                case ПЕРЕКРЫТИЕ -> areaList.add(new Floor("Перекрытие"));
                case СТЕНА -> areaList.add(new Wall("Стена"));
                default -> areaList.add(null);
            }
        }
    }
    public String createResponse(){
        int countFloor = 0, countWall = 0, count = 0;
        for (Area area: areaList){
            if (area.getClass().equals(Floor.class)) {
                countFloor += 1;
            }
            if (area.getClass().equals(Wall.class)) {
                countWall+= 1;
            }
            count += 1;
        }
        return "Число перекрытий равно" + countFloor + ",\n" +
                "Число стен равно" + countWall + ",\n" +
                "Общее число межблочных пространств равно" + count;
    }
}
