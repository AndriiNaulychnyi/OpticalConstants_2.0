package opticalconstants.service;

import lombok.extern.slf4j.Slf4j;
import opticalconstants.model.IncomingData;
import opticalconstants.model.OutputData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CalculationsService {
    private static final NumberFormat NUM_FORMAT = NumberFormat.getInstance(Locale.UK);
    private final String PATH_DOWNLOAD_FOLDER = "tmp/results/";

    private final InputFileParser inputFileParser = new InputFileParser();

    public CalculationsService() {
        new File(PATH_DOWNLOAD_FOLDER).mkdirs();
    }

    public File calculation(int d, File file) {
        List<IncomingData> incomingDataList = inputFileParser.pars(d, file);
        List<CalculationResult> calculationResultList = calculationResult(incomingDataList);
        List<OutputData> outputDataList = getOutputDataList(calculationResultList);
        return createOutputDataFile(outputDataList);
    }

    private List<CalculationResult> calculationResult(List<IncomingData> incomingDataList) {
        List<CalculationResult> calculationResultList = new ArrayList<>();
        for (IncomingData incomingData : incomingDataList) {
            log.info("{}", incomingData.getLambda());
            for (double k = 0; k <= 12; k+=0.01) {
                for (double n = 0; n <= 6; n+=0.01) {
                    CalculationResult calculationResult= new CalculationResult(k, n, incomingData);
                    if (calculationResult.getTCalc() <= (incomingData.getTFilm() + 0.01) &&
                        calculationResult.getTCalc() >= (incomingData.getTFilm() - 0.01) &&
                        calculationResult.getRCalc() <= (incomingData.getRFilm() + 0.01) &&
                        calculationResult.getRCalc() >= (incomingData.getRFilm() - 0.01)) {
                        calculationResultList.add(calculationResult);
                    }
                }
            }
        }
        return calculationResultList;
    }

    private List<OutputData> getOutputDataList(List<CalculationResult> calculationResultList) {
        List<OutputData> outputDataList = new ArrayList<>();
        for (CalculationResult calculationResult : calculationResultList) {
            outputDataList.add(new OutputData(calculationResult.getLambda(),
                                                calculationResult.getTCalc(),
                                                calculationResult.getRCalc(),
                                                calculationResult.getN(),
                                                calculationResult.getK() ));
        }
        return outputDataList;
    }

    private File createOutputDataFile(List<OutputData> outputDataList) {
        var fileName = getFileName();
        var file = new File(PATH_DOWNLOAD_FOLDER + fileName);
        try {
            file.createNewFile();
            var printWriter = new PrintWriter(file);
            for (OutputData outputData : outputDataList) {
                printWriter.println(getFormattedString(outputData));
            }
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("failed to save the data to result file '{}', error: {}", fileName, e);
        }
        return file;
    }

    private String getFileName() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return "result_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + second + ".txt";
    }

    private String getFormattedString(OutputData outputData) {
        String lambda = BigDecimal.valueOf(outputData.getLambda()).setScale(4, RoundingMode.HALF_UP).toString().replace('.', ',');
        String tFilm = BigDecimal.valueOf(outputData.getTFilm()).setScale(4, RoundingMode.HALF_UP).toString().replace('.', ',');
        String rFilm = BigDecimal.valueOf(outputData.getRFilm()).setScale(4, RoundingMode.HALF_UP).toString().replace('.', ',');
        String n = BigDecimal.valueOf(outputData.getN()).setScale(4, RoundingMode.HALF_UP).toString().replace('.', ',');
        String k = BigDecimal.valueOf(outputData.getK()).setScale(4, RoundingMode.HALF_UP).toString().replace('.', ',');
        return lambda + '\t' +
                tFilm + '\t' +
                rFilm + '\t' +
                n + '\t' +
                k;
    }
}
