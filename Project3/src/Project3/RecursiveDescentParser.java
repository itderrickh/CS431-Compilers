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
        this.getNextToken();
        if(this.currentToken != null && !(this.currentToken instanceof EOF)) {
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
            this.expression();

            representation += ")";
        } else if(currentToken instanceof TPrint) {
            representation += "new PrintStmt(";
            //Get the ( token
            this.getNextAndAccept(TLparen.class);
            this.expressionList();
            //Get the ) token
            this.accept(TRparen.class);

            representation += ")";
        }
    }

    private void expressionList() throws Exception {
        this.representation += "new Exps(";
        this.expression();
        this.getNextToken();
        if(this.currentToken != null && !(this.currentToken instanceof TRparen)) {
            this.representation += ", ";
            this.accept(TComma.class);
            this.getNextToken();
            this.expressionList();
        }

        this.representation += ")";
    }

    private void expression() throws Exception {
        this.getNextToken();
        if(currentToken instanceof TId) {
            this.representation += "new IdExp(\"" + currentToken.getText() + "\")";
        } else if(currentToken instanceof TNumber) {
            this.representation += "new NumExp(" + currentToken.getText() + ")";
        } 
        //Handle everthing else
        else {
            this.expression();
            this.getNextToken();
            if (this.currentToken instanceof TPlus ||
                this.currentToken instanceof TMinus ||
                this.currentToken instanceof TTimes ||
                this.currentToken instanceof TDivide ||
                this.currentToken instanceof TMod) {
                this.binop();
                this.expression();
            } else if(this.currentToken instanceof TLt || this.currentToken instanceof TGt) {
                this.unaryop();
            }
        }
    }

    private void unaryop() throws Exception {
        if(this.currentToken instanceof TLt) {
            //Handle the < token
            this.getNextAndAccept(TLt.class);
        } else if(this.currentToken instanceof TGt) {
            //Handle the > token
            this.getNextAndAccept(TGt.class);
        }
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