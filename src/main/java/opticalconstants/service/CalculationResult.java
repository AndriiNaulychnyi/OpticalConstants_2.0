package opticalconstants.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import opticalconstants.model.IncomingData;

import static java.lang.Math.*;

@Data
@Slf4j
public class CalculationResult {

    private Double lambda;
    private Double q;
    private Integer d;
    private Double k;
    private Double rSub;
    private Double tSub;
    private Double rExp;
    private Double tExp;

    private Double n0;
    private Double n;
    private Double n2;

    private Double a1;
    private Double a2;
    private Double a3;
    private Double b1;
    private Double b2;
    private Double b3;
    private Double c1;
    private Double c2;
    private Double c3;
    private Double d1;
    private Double d2;
    private Double d3;
    private Double tNUM;
    private Double rFNUM;
    private Double den;
    private Double rBNUM;
    private Double rF;
    private Double rB;
    private Double t;

    private Double rCalc;
    private Double tCalc;

    public CalculationResult(double k, double n, IncomingData incomingData) {
        this.k = k;
        this.n = n;
        this.n0 = 1.0;
        this.n2 = 1.43;
        this.d = incomingData.getD();
        this.lambda = incomingData.getLambda();
        this.tSub = incomingData.getTSub();
        this.rSub = incomingData.getRSub();
        this.tExp = incomingData.getTFilm();
        this.rExp = incomingData.getRFilm();
        this.q = 2 * PI / lambda;

        calculate();
//        log.info(toString());
    }

    private void calculate() {
        initA();
        initB();
        initC();
        initD();
        initTNUM();
        initRFNUM();
        initDEN();
        initRBNUM();
        initRF();
        initRB();
        initT();
        initRCalc();
        initTCalc();
    }

    private void initA() {
        a1 = (pow(n0 - n, 2) + pow(k, 2)) * (pow(n + n2, 2) + pow(k, 2));
        a2 = (pow(n0 + n, 2) + pow(k, 2)) * (pow(n + n2, 2) + pow(k, 2));
        a3 = (pow(n2 - n, 2) + pow(k, 2)) * (pow(n + n0, 2) + pow(k, 2));
    }

    private void initB() {
        b1 = (pow(n0 + n, 2) + pow(k, 2)) * (pow(n - n2, 2) + pow(k, 2));
        b2 = (pow(n0 - n, 2) + pow(k, 2)) * (pow(n - n2, 2) + pow(k, 2));
        b3 = (pow(n2 + n, 2) + pow(k, 2)) * (pow(n - n0, 2) + pow(k, 2));
    }

    private void initC() {
        c1 = (n0 * n0 - n * n - k * k) * (n * n - n2 * n2 + k * k) - (4 * n0 * n2 * k * k);
        c2 = (n0 * n0 - n * n - k * k) * (n * n - n2 * n2 + k * k) + (4 * n0 * n2 * k * k);
        c3 = (pow(n2 + n, 2) + pow(k, 2)) * (pow(n - n0, 2) + pow(k, 2));
    }

    private void initD() {
        d1 = (n * n + n0 * n2 + k * k) * (n0 - n2);
        d2 = (n * n - n0 * n2 + k * k) * (n0 + n2);
        d3 = (n * n + n0 * n2 + k * k) * (n2 - n0);
    }

    private void initTNUM() {
        tNUM = 16 * n0 * n2 * (n * n + k * k);
    }

    private void initRFNUM() {
        rFNUM = a1 * pow(E, (2 * k * q * d)) + b1 * pow(E, (-2 * k * q * d)) + 2 * c1 * cos(2 * n * q * d) - 4 * k * d1 * sin(2 * n * q * d);
    }

    private void initDEN() {
        den = a2 * pow(E, (2 * k * q * d)) + b2 * pow(E, (-2 * k * q * d)) + 2 * c2 * cos(2 * n * q * d) - 4 * k * d2 * sin(2 * n * q * d);
    }

    private void initRBNUM() {
        rBNUM = a3 * pow(E, (2 * k * q * d)) + b3 * pow(E, (-2 * k * q * d)) + 2 * c3 * cos(2 * n * q * d) - 4 * k * d3 * sin(2 * n * q * d);
    }

    private void initRF() {
        rF = rFNUM / den;
    }

    private void initRB() {
        rB = rBNUM / den;
    }

    private void initT() {
        t = tNUM / den;
    }

    private void initRCalc() {
        rCalc = rF + ((t * t * rSub) / (1 - rB * rSub));
    }

    private void initTCalc() {
        tCalc = (t * tSub) / (1 - rB * rSub);
    }

    @Override
    public String toString() {
        return "CalculationParam{" +
                "lambda=" + lambda +
//                ", q=" + q +
                ", d=" + d +
                ", k=" + k +
//                ", rSub=" + rSub +
//                ", tSub=" + tSub +
//                ", n0=" + n0 +
                ", n=" + n +
//                ", n2=" + n2 +
//                ", a1=" + a1 +
//                ", a2=" + a2 +
//                ", a3=" + a3 +
//                ", b1=" + b1 +
//                ", b2=" + b2 +
//                ", b3=" + b3 +
//                ", c1=" + c1 +
//                ", c2=" + c2 +
//                ", c3=" + c3 +
//                ", d1=" + d1 +
//                ", d2=" + d2 +
//                ", d3=" + d3 +
//                ", tNUM=" + tNUM +
//                ", rFNUM=" + rFNUM +
//                ", den=" + den +
//                ", rBNUM=" + rBNUM +
//                ", rF=" + rF +
//                ", rB=" + rB +
//                ", t=" + t +
                ", rCalc=" + rCalc +
                ", tCalc=" + tCalc +
                '}';
    }
}
