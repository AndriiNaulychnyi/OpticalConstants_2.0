package opticalconstants.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
public class CalculationsController {
    private final String PATH_UPLOAD_FOLDER = "D:\\IdeaProject\\OpticalConstants_2.0\\uploadFiles\\";

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile document) throws IOException {
        File file = new File(PATH_UPLOAD_FOLDER + document.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(document.getBytes());
        fileOutputStream.close();

        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }
}
