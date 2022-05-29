package opticalconstants.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputData {
    private Double lambda;
    private Double tFilm;
    private Double rFilm;
    private Double n;
    private Double k;

    public OutputData(Double lambda, Double tFilm, Double rFilm, Double n, Double k) {
        this.lambda = lambda;
        this.tFilm = tFilm;
        this.rFilm = rFilm;
        this.n = n;
        this.k = k;
    }
}
