package Project3;

import Project3.lexer.*;
import Project3.node.*;
import java.io.*;

public class Main{

    public static void main(String[] arguments){
        try{
            // Create a lexer instance.
            Lexer l = new Lexer(new PushbackReader
                    (new InputStreamReader(System.in), 1024));

            RecursiveDescentParser rcp = new RecursiveDescentParser(l);
            rcp.parseProgram();

            //This is a sample on how to build a useable program for the interpret program			
            StringBuilder sb = new StringBuilder();
            sb.append("package Project3.interpreter;\n");
            sb.append("public class ProgExpr {\n");
            sb.append("\tpublic static Stmt program = ");
            sb.append(rcp.toString());
            sb.append(";\n");
            sb.append("}");

            FileWriter.writeToFile("src/Project3/interpreter/ProgExpr.java", sb.toString());
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
    }
}