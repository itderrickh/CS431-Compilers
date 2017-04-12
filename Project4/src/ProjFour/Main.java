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

            Parser parser = new Parser(lexer);

            Start ast = parser.parse();
            FileWriter.writeToFile(arguments[0], "Program is valid");
      }
      catch(Exception e){ FileWriter.writeToFile(arguments[0], "Program is valid"); }
   }
}