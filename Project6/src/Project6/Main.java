package Project6;

import Project6.lexer.*;
import Project6.node.*;
import Project6.parser.*;
import java.io.*;

public class Main{

   public static void main(String[] arguments){
      try{
            Lexer lexer = new Lexer(new PushbackReader
                  (new InputStreamReader(System.in), 1024));

            Parser parser = new Parser(lexer);

            Start ast = parser.parse();
            PrintTree tree = new PrintTree();
            ast.apply(tree);  //this is what gets the depth first search going

            //Print warnings
            if(tree.getWarnings().length() > 0) {
                  System.out.println("Warning(s): ");
                  System.out.println(tree.getWarnings());
            }

            if(tree.getErrors().length() > 0) {
                  throw new Exception(tree.getErrors());
            }

            FileWriter.writeToFile(arguments[0], tree.getResult());
      }
      catch(Exception e){
            e.printStackTrace();
            //System.out.println("Error(s): \n" + e.getMessage());
      }
   }
}