package vkr.planner.model.specification.woods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pipe {
    private double x, y, z;
    private boolean isNeedToBeThermallyTnsulated;
    private String codKks;
}
