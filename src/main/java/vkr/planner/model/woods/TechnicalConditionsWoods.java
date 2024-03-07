package vkr.planner.model.woods;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
public class TechnicalConditionsWoods {

    private List<Area> areaList = new ArrayList<>();

    public List<Pipe> getAllPipeList(){
        return areaList.stream()
                .flatMap(area -> area.getPipeList().stream())
                .toList();
    }
    public void updatePipe(Pipe oldPipe, Pipe newPipe){
        Optional<Area> area = Optional.of(areaList.stream()
                .filter(a -> a.getPipeList().stream()
                        .filter(pipe -> pipe.equals(oldPipe)).isParallel())
                .findFirst()).orElse(Optional.empty());
        area.ifPresent(value -> value.getPipeList().set(value.getPipeList().indexOf(oldPipe), newPipe));
        area.ifPresent(value -> areaList.set(areaList.indexOf(value), value));
        }
}
