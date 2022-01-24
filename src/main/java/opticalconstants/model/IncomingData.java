package opticalconstants.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IncomingData {
    private Integer d;
    private List<Double> lambda;
    private List<Double> TFilm;
    private List<Double> RFilm;
    private List<Double> TSub;
    private List<Double> RSub;
}
