package Project5;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileWriter {
    private BufferedWriter writer;
    private FileWriter() {}

    public static FileWriter writeToFile(String filename, String output) {
        FileWriter f = new FileWriter();

        try {
            f.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename))));
            f.writer.write(output);
            f.writer.close();
        } catch(Exception ex) { System.out.println(ex); }
        return f;
    }
}