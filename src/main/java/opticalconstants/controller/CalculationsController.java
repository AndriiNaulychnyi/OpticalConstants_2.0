package opticalconstants.controller;

import opticalconstants.service.CalculationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Controller
public class CalculationsController {
    private static final Logger log = LoggerFactory.getLogger(CalculationsController.class);
    private final String PATH_UPLOAD_FOLDER = "tmp/input/";

    private final CalculationsService calculationService = new CalculationsService();

    public CalculationsController() {
        new File(PATH_UPLOAD_FOLDER).mkdirs();
    }

    @RequestMapping(value = "/upload",
                    method = RequestMethod.POST,
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("d") Integer d,
                                             @RequestParam("file") MultipartFile document) throws IOException {
        File file = new File(PATH_UPLOAD_FOLDER + document.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(document.getBytes());
        fileOutputStream.close();

        var resultFile = calculationService.calculation(d, file);
        var resource = new FileSystemResource(resultFile);


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
