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
            PrintTree tree = new PrintTree();
            ast.apply(tree);  //this is what gets the depth first search going

            if(tree.getErrors().length() > 0) {
                  throw new Exception(tree.getErrors());
            }

            //Print warnings
            if(tree.getWarnings().length() > 0) {
                  System.out.println(tree.getWarnings());
            }

            FileWriter.writeToFile(arguments[0], tree.getResult());
      }
      catch(Exception e){
            System.out.println("Error(s): " + e.getMessage());
      }
   }
}