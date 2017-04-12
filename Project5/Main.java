package Project5;

import Project5.lexer.*;
import Project5.node.*;
import Project5.parser.*;
import java.io.*;

public class Main{

   public static void main(String[] arguments){
      try{
            Lexer lexer = new Lexer(new PushbackReader
                  (new InputStreamReader(System.in), 1024));

            Parser parser = new Parser(lexer);

            Start ast = parser.parse();
            ast.apply(new PrintTree());  //this is what gets the depth first search going
      }
      catch(Exception e){ System.out.println("Error: " + e.getMessage()); }
   }
}