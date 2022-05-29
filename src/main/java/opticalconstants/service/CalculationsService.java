package opticalconstants.service;

import opticalconstants.model.IncomingData;
import opticalconstants.model.OutputData;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Service
@Slf4j
public class CalculationsService {
    private static final NumberFormat NUM_FORMAT = NumberFormat.getInstance(Locale.UK);
    private final String PATH_DOWNLOAD_FOLDER = "D:\\IdeaProject\\OpticalConstants_2.0\\downloadFiles\\";

    private final InputFileParser inputFileParser = new InputFileParser();

    public List<OutputData> calculation(int d, File file) {
        List<IncomingData> incomingDataList = inputFileParser.pars(d, file);
        List<CalculationResult> calculationResultList = calculationResult(incomingDataList);
        List<OutputData> outputDataList = getOutputDataList(calculationResultList);
        File outputDataFile = createOutputDataFile(outputDataList);
        System.out.println(" fssss");
        return outputDataList;
    }

    private List<CalculationResult> calculationResult(List<IncomingData> incomingDataList) {
        List<CalculationResult> calculationResultList = new ArrayList<>();
        for (IncomingData incomingData : incomingDataList) {
            log.info("" + incomingData.getLambda());
            for (double k = 0; k <= 12; k+=0.01) {
                for (double n = 0; n <= 6; n+=0.01) {
                    CalculationResult calculationResult= new CalculationResult(k, n, incomingData);
                    if (calculationResult.getTCalc() <= (incomingData.getTFilm() + 0.01) &&
                        calculationResult.getTCalc() >= (incomingData.getTFilm() - 0.01) &&
                        calculationResult.getRCalc() <= (incomingData.getRFilm() + 0.01) &&
                        calculationResult.getRCalc() >= (incomingData.getRFilm() - 0.01) &&
                        n >= k ) {
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
        String fileName = getFileName();
        File file = new File((PATH_DOWNLOAD_FOLDER + fileName));
        try {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            for (OutputData outputData : outputDataList) {
                printWriter.println(getFormattedString(outputData));
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        StringBuilder fileName = new StringBuilder("result_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + second + ".txt");
        return fileName.toString();
    }

    private String getFormattedString(OutputData outputData) {
        String lambda = BigDecimal.valueOf(outputData.getLambda()).setScale(4, BigDecimal.ROUND_HALF_UP).toString().replace ( '.', ',');
        String tFilm = BigDecimal.valueOf(outputData.getTFilm()).setScale(4, BigDecimal.ROUND_HALF_UP).toString().replace ( '.', ',');
        String rFilm = BigDecimal.valueOf(outputData.getRFilm()).setScale(4, BigDecimal.ROUND_HALF_UP).toString().replace ( '.', ',');
        String n = BigDecimal.valueOf(outputData.getN()).setScale(4, BigDecimal.ROUND_HALF_UP).toString().replace ( '.', ',');
        String k = BigDecimal.valueOf(outputData.getK()).setScale(4, BigDecimal.ROUND_HALF_UP).toString().replace ( '.', ',');
        StringBuilder stringBuilder = new StringBuilder(lambda + "\t" + tFilm + '\t' + rFilm +'\t' + n + '\t' + k);
        return stringBuilder.toString();
    }
}
