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
                int intC = 0;

                if(!Character.isWhitespace(character)) {
                    tokenizer.add(character);

                    if (character == '/') {
                        reader.mark(255);
                        intC = reader.read();
                        if (intC != 1) {
                            character = (char) intC;
                            if (character == '/') {
                                while(character != '\n' && intC != -1) {
                                    intC = reader.read();
                                    character = (char) intC;
                                }

                                result += "<TComments>";
                                tokenizer.clear();
                                continue;
                            }
                            else if (character == '*') {
                                while(intC != -1) {
                                    intC = reader.read();
                                    character = (char) intC;
                                    if (character == '*') {
                                        intC = reader.read();
                                        character = (char) intC;
                                        if (character == '/') {
                                            break;
                                        }
                                    }
                                }

                                result += "<TComments>";
                                tokenizer.clear();
                                continue;
                            }
                            else {
                                reader.reset();
                            }
                        }
                    }

                    if(tokenizer.isSymbol()) {
                        result += tokenizer.getToken();
                        tokenizer.clear();
                        continue;
                    }

                    if(tokenizer.isNumber()) {  //check if first value is number
                        reader.mark(255);
                        intC = reader.read();
                        if(intC != -1) {
                            character = (char) intC;
                            while(tokenizer.isNumber(character) && intC != -1) {
                                tokenizer.add(character);
                                reader.mark(255);
                                intC = reader.read();
                                character = (char) intC;
                            }

                            reader.reset();
                            result += tokenizer.getToken();
                            tokenizer.clear();
                        }
                        
                        continue;
                    }

                    if(tokenizer.isIdentifier()) {  //check if first value is number
                        reader.mark(255);
                        intC = reader.read();
                        if(intC != -1) {
                            character = (char) intC;
                            while(tokenizer.isIdentifier(character) && !Character.isWhitespace(character) && intC != -1) {
                                tokenizer.add(character);
                                reader.mark(255);
                                intC = reader.read();
                                character = (char) intC;
                            }

                            if(tokenizer.getString().equals("System") && character == '.') {
                                tokenizer.add(character);
                                reader.mark(255);
                                while(!tokenizer.getString().equals("System.out.println") && intC != -1) {
                                    intC = reader.read();
                                    tokenizer.add((char)intC);
                                }

                                if(tokenizer.getString().equals("System.out.println")) {
                                    result += "<TPrint>";
                                    tokenizer.clear();
                                }
                            } else {
                                reader.reset();
                                result += tokenizer.getToken();
                                tokenizer.clear();
                            }
                        }
                        
                        continue;
                    }
                }
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