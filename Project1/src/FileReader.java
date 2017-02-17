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

                if(tokenizer.isNumber()) {  //check if first value is number
                    boolean sawDecimal = false;
                    int numberCharacter = reader.read();   
                    while(numberCharacter != -1) {
                        character = (char)numberCharacter;
                        tokenizer.add(character);
                        //TODO the way I was thinking about this in the way this is currently set up would be to add until that character makes it not a number,
                        // then remove that element, get the tokens for the elements leading up to it, then clear and add that element back into to the tokenizer.
                        //problem is, the loop starts over by getting the next token, so what happens to the one we added back?
                        if (!(tokenizer.isValid() && tokenizer.isNumber())) {
                            tokenizer.removeLast(); 
                            result += tokenizer.getToken();
                            tokenizer.clear();
                            tokenizer.add(character);
                            break;
                        }
                        
                        
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