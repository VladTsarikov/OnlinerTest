package framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Properties;

/**
 * Reading information from property file
 */

public class PropertyReader {

    private final static String FILE_PATH = "src/test/java/resources/config.properties";
    private static FileInputStream fileIS;
    private static Properties properties;

    static {
        try {

            String absolutePath = FileSystems.getDefault().getPath(FILE_PATH).normalize()
                    .toAbsolutePath().toString();
            fileIS = new FileInputStream(absolutePath);
            properties = new Properties();
            properties.load(fileIS);

        } catch (IOException e) {
            System.out.println("File not found");
        } finally {
            if (fileIS != null)
                try {
                    fileIS.close();
                } catch (IOException e) {
                    System.out.println("File not found");
                }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
