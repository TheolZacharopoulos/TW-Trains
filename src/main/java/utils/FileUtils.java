package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {
    public static String readFileContents(String filename) throws IOException {
        final BufferedReader file = new BufferedReader(new FileReader(filename));
        final StringBuilder data = new StringBuilder();

        String line;
        while ((line = file.readLine()) != null) {
            data.append(line);
        }

        return data.toString();
    }

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
