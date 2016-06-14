package br.com.itengine.score.controller;

import br.com.itengine.score.Application;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Thiago Almeida.
 */

@RestController
@RequestMapping("/rest/image")
public class ImageController {

    @RequestMapping(value="/league",method = RequestMethod.POST)
    public void handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(Application.ROOT + "/" + name)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
            }
            catch (Exception e) {
            }
        }
    }
    @RequestMapping(value = "/league",method = RequestMethod.GET)
    public ResponseEntity<byte[]> testphoto() throws IOException {
        InputStream in =  new FileInputStream(Application.ROOT+"/"+"1.png");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return new ResponseEntity<byte[]>(buffer.toByteArray(), HttpStatus.CREATED);
    }

}
