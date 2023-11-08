package vkr.planner.model.woods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
}
