package Project3;

public class RecursiveDescentParser {
    private Lexer lexer;
    private Token currentToken;
    private String representation = "";

    public class RecursiveDescentParser(Lexer l) { this.lexer = l; }

    public void parseProgram() {
        this.currentToken = this.lexer.next();
        this.representation += "new Stmts(";
        this.statements();
    }

    private void error() throws Exception {
        throw new Exception("Unexpected token: " + this.currentToken.getString());
    }

    private void accept(Type t) {
        if(!this.currentToken instanceof t) {
            this.error();
        }
    }

    private void getNextAndAccept(Type t) {
        this.getNextToken();
        this.accept(t);
    }

    private void statements() {
        representation += "new Stmts(";
        this.statement();
        this.getNextToken();
        if(this.currentToken != null) {
            //There are more statements
            this.accept(TSemicolon.getClass());
            this.statements();
        }

        representation += ")";
    }

    private void statement() {
        if(currentToken instanceof TId) {
            representation += "new AssignStmt(";

            //Get the < token
            this.getNextAndAccept(TLt.getClass());
            //Get the - token
            this.getNextAndAccept(TMinus.getClass());
            //Get the - token
            this.getNextAndAccept(TMinus.getClass());
            this.expression();

            representation += ")";
        } else if(currentToken instanceof TPrint) {
            representation += "new PrintStmt(";
            //Get the ( token
            this.getNextAndAccept(TLParen.getClass());
            this.expressionList();
            //Get the ) token
            this.getNextAndAccept(TRParen.getClass());

            representation += ")";
        }
    }

    private void expressionList() {
        //STOPPED HERE DOING REPRESENTATIONS


        this.expression();
        this.getNextToken();
        if(this.currentToken != null) {
            this.accept(TComma.getClass());
            this.expressionList();
        }
    }

    private void expression() {
        this.getNextToken();
        if(currentToken instanceof TId) {

        } else if(currentToken instanceof TNumber) {

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

    private void unaryop() {
        if(this.currentToken instanceof TLt) {
            //Handle the < token
            this.getNextAndAccept(TLt.getClass());
        } else if(this.currentToken instanceof TGt) {
            //Handle the > token
            this.getNextAndAccept(TGt.getClass());
        }
    }

    private void binop() {
        if(this.currentToken instanceof TPlus) {
            //Handle the < token
            this.getNextAndAccept(TPlus.getClass());
        } else if(this.currentToken instanceof TMinus) {
            //Handle the > token
            this.getNextAndAccept(TMinus.getClass());
        } else if(this.currentToken instanceof TDivide) {
            //Handle the > token
            this.getNextAndAccept(TDivide.getClass());
        } else if(this.currentToken instanceof TTimes) {
            //Handle the > token
            this.getNextAndAccept(TTimes.getClass());
        }
    }

    private void getNextToken() {
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