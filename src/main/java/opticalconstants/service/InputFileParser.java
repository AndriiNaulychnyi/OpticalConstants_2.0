package opticalconstants.service;

import opticalconstants.model.IncomingData;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class InputFileParser {

    private static final NumberFormat NUM_FORMAT = NumberFormat.getInstance(Locale.FRANCE);

    public List<IncomingData> pars(File file) {
        List<IncomingData> incomingDataList = new ArrayList<>();

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String[] splitLine = line.split("\\s");
                incomingDataList.add(saveIncomingData(splitLine));
            }
        } catch (FileNotFoundException | ParseException e) {
            e.fillInStackTrace();
        }
        return incomingDataList;
    }

    private IncomingData saveIncomingData(String[] splitLine) throws ParseException {
        if (splitLine.length != 5) {
            throw new IllegalArgumentException(
                    "Provided invalid array to parse. Expected length is 5, received " + splitLine.length);
        }
        IncomingData incomingData = new IncomingData();
        incomingData.setLambda(NUM_FORMAT.parse(splitLine[0]).doubleValue());
        incomingData.setTFilm(NUM_FORMAT.parse(splitLine[1]).doubleValue());
        incomingData.setRFilm(NUM_FORMAT.parse(splitLine[2]).doubleValue());
        incomingData.setTSub(NUM_FORMAT.parse(splitLine[3]).doubleValue());
        incomingData.setRSub(NUM_FORMAT.parse(splitLine[4]).doubleValue());
        return incomingData;
    }
}
