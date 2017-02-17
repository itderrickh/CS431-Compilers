import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileReader {
    private BufferedReader reader;
    private Tokenizer tokenizer;
    private FileReader() {}

    public static FileReader newFileReader(String filename, Tokenizer tokenizer) {
        FileReader f = new FileReader();

        try {
            f.reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
        } catch(FileNotFoundException ex) { System.out.println(ex); }

        f.tokenizer = tokenizer;
        return f;
    }

    public String getTokens() {
        String result = "";
        int c;

        try {
            while((c = reader.read()) != -1) {
                char character = (char) c;
                tokenizer.add(character);

                //Handle the symbol case
                if(tokenizer.isSymbol()) {
                    result += tokenizer.getToken();
                    tokenizer.clear();
                }

                //Handle the comments
                if(tokenizer.isComment()) {
                    character = (char) reader.read();
                    while(character != '\n') {
                        tokenizer.add(character);
                    }

                    result += tokenizer.getToken();
                    tokenizer.clear();
                }

                if(tokenizer.isNumber()) {
                    while(tokenizer.isNumber() && tokenizer.isValid()) {
                        character = (char) reader.read();
                        tokenizer.add(character);
                    }
                }

                //HERE WAS WHERE I WAS WORKING.
            }
        }
        catch(IOException ex) {}

        return result;
    }

    public void close() {
        try {
            reader.close();
        } catch(IOException ex) {}
    }
}