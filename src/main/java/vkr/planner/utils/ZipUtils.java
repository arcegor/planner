package vkr.planner.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    public static Map<String, InputStream> unzip(byte[] bytes){
        Map<String, InputStream> stringInputStreamMap = new HashMap<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try(ZipInputStream zin = new ZipInputStream(inputStream, StandardCharsets.UTF_8))
        {
            String name;
            ZipEntry entry = zin.getNextEntry();
            while(entry!=null){
                long buffer;
                name = entry.getName().split("\\.")[0];
                buffer = entry.getSize();
                ByteArrayOutputStream baos = new ByteArrayOutputStream((int) buffer);

                for (int c = zin.read(); c != -1; c = zin.read()) {
                    baos.write(c);
                }
                InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());
                stringInputStreamMap.put(name, inputStream1);
                baos.flush();
                zin.closeEntry();
                baos.close();
                entry = zin.getNextEntry();
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    return stringInputStreamMap;
    }
}
