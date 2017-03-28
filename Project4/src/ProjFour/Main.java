package ProjFour;

import ProjFour.lexer.*;
import ProjFour.node.*;
import ProjFour.parser.*;
import java.io.*;

public class Main{

   public static void main(String[] arguments){
      boolean debug = false;
      try{
            Lexer lexer = new Lexer(new PushbackReader
                  (new InputStreamReader(System.in), 1024));

            if(debug) {
                  String result = "";
                  Token t = lexer.next();
                  while (!t.getText().equals("")){
                              if(!t.getClass().getSimpleName().equals("TBlank")) {
                                    //System.out.print("<" + t.getClass().getSimpleName() + ">");
                                    result += "<" + t.getClass().getSimpleName() + ">";
                              }
                              t = lexer.next();
                  }
                  System.out.println(result);
            } else {
                  Parser parser = new Parser(lexer);

                  Start ast = parser.parse();
                  System.out.println("It's valid!");
            }
      }
      catch(Exception e){ System.out.println("NOT VALID: " + e.getMessage()); }
   }
}