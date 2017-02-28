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
                //For testing purposes
                //String result = "";
                //Token t = l.next();
                //while (!t.getText().equals("")){
                //        if(!t.getClass().getSimpleName().equals("TBlank")) {
                                //For testing purposes
                //                System.out.println(t.getClass().getSimpleName());
                                //result += "<" + t.getClass().getSimpleName() + ">";
                //        }
                //        t = l.next();
                //}

                //For testing purposes
                //FileWriter.writeToFile("temp.txt", result);

                //This is a sample on how to build a useable program for the interpret program			
                StringBuilder sb = new StringBuilder();
                sb.append("package Project3.interpreter;\n");
                sb.append("public class ProgExpr {\n");
                //sb.append("\tpublic static Stmt program = new PrintStmt(new Exps(new NumExp(30)));\n");
                sb.append("\tpublic static Stmt program = ");
                sb.append(rcp.toString());
                sb.append(";\n");
                sb.append("}");

                FileWriter.writeToFile("src/Project3/interpreter/ProgExpr.java", sb.toString());
            }
            catch(Exception e){ System.out.println(e.getMessage()); }
    }
}