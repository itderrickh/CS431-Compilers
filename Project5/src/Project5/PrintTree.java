package Project5;

import Project5.analysis.*;
import Project5.node.*;
import java.util.*;


class PrintTree extends DepthFirstAdapter
{
    private Stack<Object> flapjacks = new Stack<Object>();
    private SymbolTable symbolTable;
    private StringBuilder mipsString;
    private StringBuilder data;

    private int nextRegister = 0;

 	public PrintTree() {
        this.symbolTable = new SymbolTable();
        mipsString = new StringBuilder();
        data = new StringBuilder();
	}

    public String getResult() {
        return mipsString.toString() + "\n" + data.toString();
    }

    public void caseAProg(AProg node) {
        mipsString.append(".text\n");
        mipsString.append(".globl main\n");

        node.getClassmethodstmts().apply(this);

        //Write the end of program command
        mipsString.append("\tli $v0, 10\n").append("\tsyscall\n");

        //Write the data section
        data.append(".data\n");
        data.append("\tNEWLINE: .asciiz \"\\n\"\n");
        for(String s : this.symbolTable.getKeys()) {
            Symbol value = this.symbolTable.getValue(s);
            
            if(value != null) {
                data.append("\t");

                if(value.getValue() instanceof String) {
                    data.append(s).append(": ").append(".asciiz ").append(value.getValue().toString());
                } else if(value.getValue() instanceof Double) {
                    System.out.println("Double: " + value.getValue().toString());
                    //Handle double differently
                } else if(value.getValue() instanceof Integer) {
                    data.append(s).append(": ").append(".word ").append(value.getValue().toString());
                }
                data.append("\n");
            } else {
                //Turn this into a warning later
                System.out.println("Unassigned variable: " + s);
            }
        }
    }

    public void caseAMorestatementsClassmethodstmts(AMorestatementsClassmethodstmts node) {
        node.getClassmethodstmts().apply(this);
        node.getClassmethodstmt().apply(this);
    }

    public void caseAFunctiondefClassmethodstmt(AFunctiondefClassmethodstmt node) {
        node.getId().apply(this);
        String id = flapjacks.pop().toString().toLowerCase();
        mipsString.append(id).append(": \n");

        node.getStmtseq().apply(this);
    }

    public void caseAMorestatementsStmtseq(AMorestatementsStmtseq node) {
        node.getStmt().apply(this);
        node.getStmtseq().apply(this);
    }

    public void caseAIdtail(AIdtail node) {
        //Handle id tail
        //node.getId().apply(this);
    }

    /*****************************************
    * START STMT AREA                        *
    *****************************************/
    public void caseAAssignexpStmt(AAssignexpStmt node) {
        node.getId().apply(this);
        node.getExpr().apply(this);

        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();
        String type = "";
        if(value instanceof Integer) {
            type = "INTEGER";
        } else if(value instanceof Double) {
            type = "REAL";
        } else if(value instanceof Boolean) {
            type = "BOOLEAN";
        }

        //Check the value to make sure the old type still is legitimate
        symbolTable.update(id, new Symbol(id, value, type));
    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {
        node.getId().apply(this);
        node.getStringlit().apply(this);

        String value = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        //Check the value to make sure the old type still is legitimate
        symbolTable.update(id, new Symbol(id, value, "STRING"));
    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {
        node.getId().apply(this);

        String id = flapjacks.pop().toString();
        symbolTable.add(id, null);
    }

    public void caseAIfStmt(AIfStmt node) {
        
    }

    public void caseAWhileStmt(AWhileStmt node) {

    }

    public void caseAForStmt(AForStmt node) {

    }

    public void caseAGetcommandStmt(AGetcommandStmt node) {
        //Check the type of the id

        //Write assembly code accordingly
    }

    public void caseAPutcommandStmt(APutcommandStmt node) {
        String currReg = "$t" + this.nextRegister;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        String type = symbolTable.getValue(id).getType();
        if(type.equals("STRING")) {
            mipsString.append("\tli $v0 4\n").append("\tla $a0, ").append(id).append("\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("INTEGER")) {
            mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
            mipsString.append("\tli $v0 1\n").append("\tadd $a0, ").append(currReg).append(", $zero\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("REAL")) {

        } else if(type.equals("BOOLEAN")) {

        }

        //Print a newline bruh
        mipsString.append("\tli $v0, 4\n").append("\tla $a0, NEWLINE\n").append("\tsyscall\n");

        this.nextRegister++;
    }

    public void caseAIncrementStmt(AIncrementStmt node) {
        String currReg = "$t" + this.nextRegister;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
        mipsString.append("\tadd ").append(currReg).append(", ").append(currReg).append(", 1\n");
        mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
    }

    public void caseADecrementStmt(ADecrementStmt node) {
        String currReg = "$t" + this.nextRegister;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
        mipsString.append("\tsub ").append(currReg).append(", ").append(currReg).append(", 1\n");
        mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
    }

    public void caseAAssignclassStmt(AAssignclassStmt node) {

    }

    public void caseAListexpStmt(AListexpStmt node) {

    }

    public void caseAAssignmethodStmt(AAssignmethodStmt node) {

    }

    public void caseAReturnStmt(AReturnStmt node) {

    }

    public void caseAAssignbooleanStmt(AAssignbooleanStmt node) {

    }

    public void caseASwitchStmt(ASwitchStmt node) {

    }

    /*****************************************
    * END STMT AREA                          *
    *****************************************/

    /*****************************************
    * START TYPE AREA                        *
    *****************************************/
    public void caseAIntType(AIntType node) {

    }

    public void caseARealType(ARealType node) {

    }

    public void caseAStringType(AStringType node) {

    }

    public void caseABoolType(ABoolType node) {

    }

    public void caseAVoidType(AVoidType node) {

    }

    public void caseAIdType(AIdType node) {

    }

    /*****************************************
    * END TYPE AREA                          *
    *****************************************/

    /*****************************************
    * START EXPR AREA                        *
    *****************************************/

    public void caseATermExpr(ATermExpr node) {
        node.getTerm().apply(this);
    }

    /*****************************************
    * END EXPR AREA                          *
    *****************************************/

    /*****************************************
    * START Term AREA                        *
    *****************************************/

    public void caseAFactorTerm(AFactorTerm node) {
        node.getFactor().apply(this);
    }

    /*****************************************
    * END TERM AREA                          *
    *****************************************/

    /*****************************************
    * START FACTOR AREA                        *
    *****************************************/

    public void caseAIntegerFactor(AIntegerFactor node) {
        node.getIntnum().apply(this);
    }

    /*****************************************
    * END FACTOR AREA                          *
    *****************************************/

    /*****************************************
    * START TOKEN AREA                       *
    *****************************************/
    public void caseTId(TId node) {
        flapjacks.push(node.getText());
    }

    public void caseTIntnum(TIntnum node) {
        flapjacks.push(Integer.parseInt(node.getText()));
    }

    public void caseTRealnum(TRealnum node) {
        flapjacks.push(Double.parseDouble(node.getText()));
    }

    public void caseTStringlit(TStringlit node) {
        flapjacks.push(node.getText());
    }
}