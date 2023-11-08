package vkr.planner.convert;

import com.google.common.collect.ImmutableMap;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import vkr.planner.model.woods.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter
@Setter
public class WoodsModelConvert {
    enum CellPatternEnumType{
        ТАБЛИЦА,
        ПЕРЕКРЫТИЕ,
        СТЕНА;
    }
    private Map<CellPatternEnumType, String> cellPatternEnumTypeStringMap;
    private Map<Integer, List<String>> excelTable;
    public static final String NO_DESCRIPTION = "Не удалось извлечь описание области";
    public static final String NO_MATCHES = "Совпадений не найдено";
    public static final String KKS_NOT_FOUND = "KKS код не найден";
    public static final String RESERVE_KKS = "Резерв";
    public static final String NEIGHBOUR_AREAS_NOT_FOUND = "Смежные помещения не найдены";

    public static Integer KKS_INDEX = 1;
    public static Integer COORDINATES_INDEX = 2;
    public static Integer THERMALLY_INSULATED_INDEX = 10;
    public static Integer PIPE_NUMBER = 0;
    public static Integer NEIGHBOUR_AREAS = 13;
    @PostConstruct
    public void init(){
        this.cellPatternEnumTypeStringMap = ImmutableMap.<CellPatternEnumType, String>builder()
                .put(CellPatternEnumType.СТЕНА, "с отметки")
                .put(CellPatternEnumType.ПЕРЕКРЫТИЕ, "на отметке")
                .put(CellPatternEnumType.ТАБЛИЦА, "таблицу")
                .build();
    }
    private Pattern patternLevel = Pattern.compile("[-+]\\d+,\\d+");
    private Pattern patternKks = Pattern.compile("[A-Z]+(\\d)*");
    private Pattern patternPipeNumber = Pattern.compile("[0-9]+[,.][0-9]*");
    private Pattern thermallyNotInsulatedPattern = Pattern.compile("[0\\- ]");
    private Pattern neighbouringAreasPattern = Pattern.compile("\\w+\\s*/\\s*\\w+");
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
    public TechnicalDescriptionWoods convertMapToTechnicalDescriptionWoods(Map<Integer, List<String>> excelTable){
        this.excelTable = excelTable;
        TechnicalDescriptionWoods technicalDescriptionWoods = new TechnicalDescriptionWoods();
        List<Area> areaList = technicalDescriptionWoods.getAreaList();
        int shift = 0;
        try {
            for (Integer key: excelTable.keySet()){
                if (key <= shift) continue; // пропускаем итерацию по строкам, которые уже были считаны
                Optional<CellPatternEnumType> cellPatternEnumType = parseRow(excelTable.get(key));
                if (cellPatternEnumType.isEmpty())
                    continue;
                String description = getAreaDescriptionFromRow(excelTable.get(key)).orElse(NO_DESCRIPTION);
                List<Double> levels = getMatchers(description);
                switch (cellPatternEnumType.get()){
                    case ПЕРЕКРЫТИЕ -> {
                        Floor floor = new Floor(AreaType.FLOOR);
                        floor.setLevel(levels.get(0));
                        areaList.add(parsePipesInArea(key, floor));
                        shift = key + floor.getPipeList().size() * 3;
                    }
                    case СТЕНА -> {
                        if (levels.size() == 1)
                            break;
                        Wall wall = new Wall(AreaType.WALL);
                        wall.setLevel(levels.get(0));
                        wall.setTop(levels.get(1));
                        areaList.add(parsePipesInArea(key, wall));
                        shift = key + wall.getPipeList().size() * 3;
                    }
                }
            }
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return technicalDescriptionWoods;
    }

    private Area parsePipesInArea(Integer key, Area area){
        try {
            boolean stop = false;
            while (!stop){
                if (excelTable.get(key + 1).size() == 1)
                    break;
                Optional<String> kks = extractKks(excelTable.get(key + 1).get(KKS_INDEX));
                Optional<String> num = extractPipeNumber(excelTable.get(key + 1).get(PIPE_NUMBER));
                Optional<List<String>> neighbouringAreas = extractNeighbouringAreas(excelTable.get(key + 1).get(NEIGHBOUR_AREAS));
                if (num.isPresent()){
                    area.getPipeList().add(new Pipe(
                            (int) Double.parseDouble(num.orElse(String.valueOf(key + 1))),
                            Double.parseDouble(excelTable.get(key + 1).get(COORDINATES_INDEX)) / 1000, // x
                            Double.parseDouble(excelTable.get(key + 2).get(COORDINATES_INDEX)) / 1000, // y
                            Double.parseDouble(excelTable.get(key + 3).get(COORDINATES_INDEX)) / 1000, // z
                            isNeedToBeThermallyTnsulated(excelTable.get(key + 1).get(THERMALLY_INSULATED_INDEX)), // термоизоляция
                            kks.orElse(KKS_NOT_FOUND), // kks
                            neighbouringAreas.orElse(Collections.singletonList(NEIGHBOUR_AREAS_NOT_FOUND)), // смежные помещения
                            area.getLevel() // уровень пола помещения
                    ));
                    key += 3;
                }
                else stop = true;
            }
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
        return area;
    }
    private Optional<String> extractKks(String kks){
        if (kks.trim().equalsIgnoreCase(RESERVE_KKS))
            return RESERVE_KKS.describeConstable();
        Matcher matcher = patternKks.matcher(kks);
        return matcher.find() ? matcher.group().describeConstable() : Optional.empty();
    }
    private Optional<String> extractPipeNumber(String s){
        Matcher matcher = patternPipeNumber.matcher(s);
        return matcher.matches() ? matcher.group().describeConstable() : Optional.empty();
    }
    private Optional<List<String>> extractNeighbouringAreas(String s){
        Matcher matcher = neighbouringAreasPattern.matcher(s);
        if (matcher.find())
            return Optional.of(Arrays.stream(matcher.group().split("/"))
                    .map(String::trim).toList());
        return Optional.empty();
    }
    private List<Double> getMatchers(String s){
        List<Double> levels = new ArrayList<>();
        Matcher matcher = patternLevel.matcher(s);
        while (matcher.find()) {
            levels.add(Double.valueOf(matcher.group().replaceAll(",", ".")));
        }
        return levels;
    }
    private Optional<String> getAreaDescriptionFromRow(List<String> row){
        return row.stream()
                .filter(e -> !e.trim().isEmpty())
                .findFirst();
    }
    private boolean isNeedToBeThermallyTnsulated(String s){
        s = s.trim().replaceAll(".0", "").replaceAll("0", "");
        return !(thermallyNotInsulatedPattern.matcher(s).find() || s.isEmpty());
    }
}
