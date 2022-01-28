package opticalconstants.service;

import opticalconstants.model.IncomingData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CalculationsService {

    private final InputFileParser inputFileParser = new InputFileParser();

    public void calculation(int d, File file) {
        List<IncomingData> incomingDataList = inputFileParser.pars(file);

    }
}
