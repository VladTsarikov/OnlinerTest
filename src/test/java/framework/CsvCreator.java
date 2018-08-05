package framework;

import java.io.*;
import java.util.List;

/**
 * Class for creating a cvs file
 * Writing strings to the file from ArrayList
 */

public class CsvCreator {

    private static File file = null;
    private static final String FILE_CSV_PATH = PropertyReader.getProperty("FileCsvPath");
    private static final String CSV_ENCODING = "Utf-8";

    private static File getFile() {
        return file;
    }


    public static void createFile(List<String> opinions) {

        file = new File(FILE_CSV_PATH);

        String absolutePath = null;

        try {
            absolutePath = file.getCanonicalPath();
        } catch (IOException e) {
            System.out.println("Failed to get absolute path");
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(absolutePath),
                    CSV_ENCODING));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding is not supported");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        for (int i = 0; i < opinions.size(); i++) {
            pw.print(opinions.get(i) + "\n");
        }
        pw.close();

    }

    public static void isExist(){
        if(getFile() != null)
            getFile().delete();
    }

}
