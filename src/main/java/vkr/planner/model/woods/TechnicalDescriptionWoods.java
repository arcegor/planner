package vkr.planner.model.woods;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TechnicalDescriptionWoods{

    private List<Area> areaList = new ArrayList<>();
}
