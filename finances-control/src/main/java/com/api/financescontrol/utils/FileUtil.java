package com.api.financescontrol.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;


@Component
public class FileUtil {

    public String convertImageToBase64(File file){
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public File convertBase64ToImage(String encodedString, String outputFileName){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        try {
            FileUtils.writeByteArrayToFile(new File(outputFileName), decodedBytes);
            return new File(outputFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File convertMultipartFileToFile(MultipartFile multipart){
        File convFile = new File(multipart.getName());
        try {
            multipart.transferTo(convFile);
            return convFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
