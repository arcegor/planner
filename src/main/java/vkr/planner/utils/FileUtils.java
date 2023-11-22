package vkr.planner.utils;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileUtils {

    public static Map<String, InputStream> getInput(MultipartFile[] files) throws IOException {
        Map<String, InputStream> stringInputStreamMap = new HashMap<>();
        Tika tika = new Tika();
        if (files.length == 1){ // Если архив
            if (tika.detect(files[0].getInputStream()).contains("zip")){
                return ZipUtils.unzip(files[0].getBytes());
            }
        }
        if (files.length != 0){ // Если excel по отдельности
            for (MultipartFile multipartFile : files){
                stringInputStreamMap.put(Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[0],
                        multipartFile.getInputStream());
            }
        }
        return stringInputStreamMap;
    }
}
