package Project2;

import Project2.lexer.*;
import Project2.node.*;
import java.io.*;

public class Main{

    public static void main(String[] arguments){
            try{
                // Create a lexer instance.
                Lexer l = new Lexer(new PushbackReader
                        (new InputStreamReader(System.in), 1024));

                Token t = l.next();
                while (!t.getText().equals("")){
                        if(!t.getClass().getSimpleName().equals("TBlank")) {
                                System.out.print("<" + t.getClass().getSimpleName() + ">");
                        }
                        t = l.next();
                }
            }
            catch(Exception e){ System.out.println(e.getMessage()); }
    }
}