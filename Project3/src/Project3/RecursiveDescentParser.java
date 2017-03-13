package Project3;

import Project3.lexer.*;
import Project3.node.*;

public class RecursiveDescentParser {
    private Lexer lexer;
    private Token currentToken;
    private String representation = "";

    public RecursiveDescentParser(Lexer l) { this.lexer = l; }

    public void parseProgram() throws Exception {
        this.currentToken = this.lexer.next();
        this.statements();
    }

    private void error(String type) throws Exception {
        throw new Exception("Unexpected token: " + this.currentToken.getText() + " expected: " + type);
    }

    private void accept(Class t) throws Exception {
        if(!(this.currentToken.getClass() == t)) {
            this.error(t.toString());
        }
    }

    private void getNextAndAccept(Class t) throws Exception {
        this.getNextToken();
        this.accept(t);
    }

    private void statements() throws Exception {
        representation += "new Stmts(";
        this.statement();
        if(this.currentToken instanceof TSemicolon) {
            //There are more statements
            this.representation += ", ";
            this.accept(TSemicolon.class);
            this.getNextToken();
            this.statements();
        }

        representation += ")";
    }

    private void statement() throws Exception {
        if(currentToken instanceof TId) {
            representation += "new AssignStmt(new IdExp(\"" + this.currentToken.getText() + "\"), ";
            //Get the < token
            this.getNextAndAccept(TLt.class);
            //Get the - token
            this.getNextAndAccept(TMinus.class);
            //Get the - token
            this.getNextAndAccept(TMinus.class);
            this.getNextToken();
            this.expression();

            representation += ")";
        } else if(currentToken instanceof TPrint) {
            representation += "new PrintStmt(";
            //Get the ( token
            this.getNextAndAccept(TLparen.class);
            this.getNextToken();
            this.expressionList();
            //Get the ) token
            this.accept(TRparen.class);
            this.getNextToken();

            representation += ")";
        }
    }

    private void expressionList() throws Exception {
        this.representation += "new Exps(";
        this.expression();
        if(this.currentToken instanceof TComma) {
            this.representation += ", ";
            this.accept(TComma.class);
            this.getNextToken();
            this.expressionList();
        }

        this.representation += ")";
    }

    private String term() throws Exception {
        String result = "";
        String endToken = "";
        String f1 = this.factor();
        this.getNextToken();

        //Handle UnaryExp
        if(currentToken instanceof TGt) {
            this.getNextAndAccept(TGt.class);
            f1 = "new UnaryExp(\">>\", " + f1 + ")";
            this.getNextToken();
        } else if(currentToken instanceof TLt) {
            this.getNextAndAccept(TLt.class);
            f1 = "new UnaryExp(\"<<\", " + f1 + ")";
            this.getNextToken();
        }

        if(currentToken instanceof TTimes || currentToken instanceof TDivide || currentToken instanceof TMod) {
            while(currentToken instanceof TTimes || currentToken instanceof TDivide || currentToken instanceof TMod) {
                if(currentToken instanceof TTimes) {
                    this.getNextToken();
                    result += "new ArithExp(\"*\", ";
                    result += this.factor();
                    result += ", ";
                    endToken += ")"; 
                } else if(currentToken instanceof TDivide) {
                    this.getNextToken();
                    result += "new ArithExp(\"/\", ";
                    result += this.factor();
                    result += ", ";
                    endToken += ")"; 
                } else if(currentToken instanceof TMod) {
                    this.getNextToken();
                    result += "new ArithExp(\"%\", ";
                    result += this.factor();
                    result += ", ";
                    endToken += ")"; 
                }

                this.getNextToken();
            }
        }
        
        result += f1;
        result += endToken;
        return result;
    }

    private String factor() throws Exception {
        String result = "";
        if(currentToken instanceof TNumber) {
            result += "new NumExp(" + currentToken.getText() + ")";
        } else if(currentToken instanceof TId) {
            result += "new IdExp(\"" + currentToken.getText() + "\")";
        } else {
            //It should error and say looking for identifier
            this.error("number or identifier");
        }

        return result;
    }

    private void expression() throws Exception {
        String t1 = this.term();
        String endToken = "";

        //Handle UnaryExp
        if(currentToken instanceof TGt) {
            this.getNextAndAccept(TGt.class);
            t1 = "new UnaryExp(\">>\", " + t1 + ")";
            this.getNextToken();
        } else if(currentToken instanceof TLt) {
            this.getNextAndAccept(TLt.class);
            t1 = "new UnaryExp(\"<<\", " + t1 + ")";
            this.getNextToken();
        }

        if(currentToken instanceof TMinus || currentToken instanceof TPlus) {
            while(currentToken instanceof TMinus || currentToken instanceof TPlus) {
                if(currentToken instanceof TMinus) {
                    this.getNextToken();
                    this.representation += "new ArithExp(\"-\", ";
                    this.representation += this.term();
                    this.representation += ", ";

                    endToken += ")";
                } else if(currentToken instanceof TPlus) {
                    this.getNextToken();
                    this.representation += "new ArithExp(\"+\", ";
                    this.representation += this.term();
                    this.representation += ", ";

                    endToken += ")";
                }
            }
        }

        this.representation += t1;
        this.representation += endToken;
    }

    private void binop() throws Exception {
        if(this.currentToken instanceof TPlus) {
            //Handle the < token
            this.getNextAndAccept(TPlus.class);
        } else if(this.currentToken instanceof TMinus) {
            //Handle the > token
            this.getNextAndAccept(TMinus.class);
        } else if(this.currentToken instanceof TDivide) {
            //Handle the > token
            this.getNextAndAccept(TDivide.class);
        } else if(this.currentToken instanceof TTimes) {
            //Handle the > token
            this.getNextAndAccept(TTimes.class);
        }
    }

    private void getNextToken() throws Exception {
        Token t = this.lexer.next();
        if(!t.getClass().getSimpleName().equals("TBlank")) {
            this.currentToken = t;
        } else {
            this.getNextToken();
        }
    }

    public String toString() {
        return this.representation;
    }
}