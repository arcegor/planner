package vkr.planner.process;

import com.google.common.collect.ImmutableMap;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import vkr.planner.model.woods.Area;
import vkr.planner.model.woods.Floor;
import vkr.planner.model.woods.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Getter
@Setter
public class ForestAseBuild {
    enum CellPatternEnumType{
        ТАБЛИЦА,
        ПЕРЕКРЫТИЕ,
        СТЕНА;
    }
//    private Map<CellPatternEnumType, Pattern> cellPatternEnumTypePatternMap;
    private Map<CellPatternEnumType, String> cellPatternEnumTypeStringMap;
    private List<Area> areaList;
    private Map<Integer, List<String>> excelTable;
    public static final String NO_DESCRIPTION = "Не удалось извлечь описание области";
    public static final String NO_MATCHES = "Совпадений не найдено";
    @PostConstruct
    public void init(){
//        cellPatternEnumTypePatternMap = ImmutableMap.<CellPatternEnumType, Pattern>builder()
//                .put(CellPatternEnumType.СТЕНА, Pattern.compile(
//                        "с отметки"))
//                .put(CellPatternEnumType.ПЕРЕКРЫТИЕ, Pattern.compile("на отметке"))
//                .put(CellPatternEnumType.ТАБЛИЦА, Pattern.compile("таблицу"))
//                .build();
        cellPatternEnumTypeStringMap = ImmutableMap.<CellPatternEnumType, String>builder()
                .put(CellPatternEnumType.СТЕНА, "с отметки")
                .put(CellPatternEnumType.ПЕРЕКРЫТИЕ, "на отметке")
                .put(CellPatternEnumType.ТАБЛИЦА, "таблицу")
                .build();
    }
//    public boolean isMatchedToPattern(String cell, Pattern pattern){
//        Matcher matcher = pattern.matcher(cell);
//        return matcher.matches();
//    }
    public boolean isContainsSubstring(String cell, String pattern){
        return cell.contains(pattern);
    }
    private Optional<CellPatternEnumType> parseCell(String cell){
        return cellPatternEnumTypeStringMap.entrySet().stream()
                .filter(e -> !cell.trim().isEmpty())
                .filter(e -> isContainsSubstring(cell, e.getValue()))
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
        areaList = new ArrayList<>();
        for (Integer key: excelTable.keySet()){
            Optional<CellPatternEnumType> cellPatternEnumType = parseRow(excelTable.get(key));
            if (cellPatternEnumType.isEmpty())
                continue;
            String description = getAreaDescriptionFromRow(excelTable.get(key)).orElse(NO_DESCRIPTION);
            switch (cellPatternEnumType.get()){
                case ПЕРЕКРЫТИЕ -> areaList.add(parsePipesInArea(key, new Floor(description)));
                case СТЕНА -> areaList.add(parsePipesInArea(key, new Wall(description)));
                default -> areaList.add(null);
            }
        }
    }
    private Floor parsePipesInArea(Integer key, Floor floor){
//        `for (Integer k : excelTable.keySet()){
//            if (k.equals(key)){
//            }
//        }`
        return floor;
    }
    private Wall parsePipesInArea(Integer key, Wall wall){
        return wall;
    }
    private Optional<String> getAreaDescriptionFromRow(List<String> row){
        return row.stream()
                .filter(e -> !e.trim().isEmpty())
                .findFirst();
    }
    public String createResponse(){
        int countFloor = 0, countWall = 0, count = 0;
        if (areaList.isEmpty())
            return NO_MATCHES;
        for (Area area: areaList){
            if (area == null)
                continue;
            if (area.getClass().equals(Floor.class)) {
                countFloor += 1;
            }
            if (area.getClass().equals(Wall.class)) {
                countWall+= 1;
            }
            count += 1;
        }
        return "Число перекрытий равно " + countFloor + ", " +
                "Число стен равно " + countWall + ", " +
                "Общее число межблочных пространств равно " + count;
    }
}
