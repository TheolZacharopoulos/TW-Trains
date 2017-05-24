package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {

    /**
     * Reads the contents of a file, puts them in a string
     * @param filename the filename
     * @return a string with the file contents
     * @throws IOException in case no file exists
     */
    public static String readFileContents(String filename) throws IOException {
        final BufferedReader file = new BufferedReader(new FileReader(filename));
        final StringBuilder data = new StringBuilder();

        String line;
        while ((line = file.readLine()) != null) {
            data.append(line);
        }

        return data.toString();
    }

    /**
     * Reads the contents of a file line by line, puts them in a list of strings
     * @param filename the filename
     * @return a list of strings with the file contents
     * @throws IOException in case no file exists
     */
    public static List<String> readFileContentsLineByLine(String filename) throws IOException {
        final BufferedReader file = new BufferedReader(new FileReader(filename));
        final List<String> list = new LinkedList<>();

        String line;
        while ((line = file.readLine()) != null) {
            list.add(line);
        }

        return list;
    }
}
