package Project5;

import Project5.symbols.*;
import Project5.analysis.*;
import Project5.node.*;
import java.util.*;


class PrintTree extends DepthFirstAdapter
{
    private Stack<Object> flapjacks = new Stack<Object>();
    private SymbolTable symbolTable;
    private StringBuilder mipsString;
    private StringBuilder data;
    private StringBuilder labels;
    private int nextRegister = 0;
    private int ifLabelCounter = 0;
    private int forLabelCounter = 0;
    private int whileLabelCounter = 0;
    private int methodLabelCounter = 0;

 	public PrintTree() {
        this.symbolTable = new SymbolTable();
        mipsString = new StringBuilder();
        data = new StringBuilder();
        labels = new StringBuilder();
	}

    public String incrementRegister() {
        String next = "$t" + this.nextRegister;
        //Loop back once we hit 7
        if(this.nextRegister > 7) {
            this.nextRegister = 0;
        } else {
            this.nextRegister++;
        }

        return next;
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

        //append all the other labels for branches and the like
        mipsString.append(labels);

        //Write the data section
        data.append(".data\n");
        data.append("\tNEWLINE: .asciiz \"\\n\"\n");
        for(String s : this.symbolTable.getKeys()) {
            Symbol value = this.symbolTable.getValue(s);
            
            if(value != null) {
                data.append("\t");

                if(value.getValue() == null) {
                    //Set the variable to the default value
                    if(value.getType().equals("INT")) {
                        data.append(s).append(": ").append(".word 0");
                    } else if(value.getType().equals("BOOLEAN")) {
                        data.append(s).append(": ").append(".word 0");
                    } else if(value.getType().equals("STRING")) {
                        data.append(s).append(": ").append(".asciiz \"\"");
                    } else if(value.getType().equals("REAL")) {
                        //IDK
                    }
                } else if(value.getValue() instanceof String) {
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
                System.out.println("Undeclared variable: " + s);
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
            type = "INT";
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
        node.getType().apply(this);
        String type = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        symbolTable.add(id, new Symbol(id, null, type));
    }

    public void caseAIfStmt(AIfStmt node) {
        //get the condition
        node.getIdbool().apply(this);
        //String condition = 

        node.getStmtseq().apply(this);
    }

    public void caseAWhileStmt(AWhileStmt node) {

    }

    public void caseAForStmt(AForStmt node) {

    }

    public void caseAGetcommandStmt(AGetcommandStmt node) {
        //Check the type of the id
        String currReg = this.incrementRegister();
        String nextReg = this.incrementRegister();
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol sym = this.symbolTable.getValue(id);

        if(sym.getType().equals("INT")) {
            mipsString.append("\taddi $v0, $zero, 5\n\tsyscall\n");
            mipsString.append("\tadd ").append(currReg).append(", $zero, $v0\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
        }    

        this.nextRegister += 2;
    }

    public void caseAPutcommandStmt(APutcommandStmt node) {
        String currReg = this.incrementRegister();

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        String type = symbolTable.getValue(id).getType();
        if(type.equals("STRING")) {
            mipsString.append("\tli $v0, 4\n").append("\tla $a0, ").append(id).append("\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("INT")) {
            mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
            mipsString.append("\tli $v0, 1\n").append("\tadd $a0, ").append(currReg).append(", $zero\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("REAL")) {
            //$f12 is used for printing floats, apparently
            mipsString.append("\tmov.s $f12, ").append(id).append("\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("BOOLEAN")) {

        }

        //Print a newline bruh
        mipsString.append("\tli $v0, 4\n").append("\tla $a0, NEWLINE\n").append("\tsyscall\n");
    }

    public void caseAIncrementStmt(AIncrementStmt node) {
        String currReg = this.incrementRegister();

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
        mipsString.append("\tadd ").append(currReg).append(", ").append(currReg).append(", 1\n");
        mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
    }

    public void caseADecrementStmt(ADecrementStmt node) {
        String currReg = this.incrementRegister();

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
        node.getInt().apply(this);
    }

    public void caseARealType(ARealType node) {
        node.getReal().apply(this);
    }

    public void caseAStringType(AStringType node) {
        node.getString().apply(this);
    }

    public void caseABoolType(ABoolType node) {
        node.getBool().apply(this);
    }

    public void caseAVoidType(AVoidType node) {
        //shrug
    }

    public void caseAIdType(AIdType node) {
        //Don't handle this yet
    }

    public void caseAVarIdbool(AVarIdbool node) {
        node.getId().apply(this);
    }

    public void caseABoolIdbool(ABoolIdbool node) {
        node.getBoolean().apply(this);
    }

    public void caseATrueBoolean(ATrueBoolean node) {
        node.getTrue().apply(this);
    }

    public void caseAFalseBoolean(AFalseBoolean node) {
        node.getFalse().apply(this);
    }

    public void caseACondBoolean(ACondBoolean node) {
        node.getExpr().apply(this);
        node.getCond().apply(this);
        node.getRight().apply(this);
        String rightExp = flapjacks.pop().toString();
        String cond = flapjacks.pop().toString();
        String leftExp = flapjacks.pop().toString();
        //mipsString.append(cond + )

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

    public void caseAAddopExpr(AAddopExpr node) {
        node.getExpr().apply(this);
        node.getAddop().apply(this);
        node.getTerm().apply(this);
        Object rightExpr = flapjacks.pop();
        String addOp = flapjacks.pop().toString();
        Object leftExpr = flapjacks.pop();
        mipsString.append(addOp + "$t" + nextRegister);
        mipsString.append(leftExpr.toString());
        mipsString.append(rightExpr.toString());
    }

    public void caseAPlusAddop(APlusAddop node) {
        node.getPlus().apply(this);
    }

    public void caseAMinusAddop(AMinusAddop node) {
        node.getMinus().apply(this);
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

    public void caseARealFactor(ARealFactor node) {

    }

    public void caseAIdFactor(AIdFactor node) {
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        String newRegister = "$t" + this.nextRegister;
        mipsString.append("li " + newRegister + symbolTable.getValue(id));
        flapjacks.push(newRegister);
    }


    /*****************************************
    * END FACTOR AREA                          *
    *****************************************/

    /*****************************************
    * START CONDITION AREA                   *
    *****************************************/

    public void caseADbleqlCond(ADbleqlCond node) {
        node.getDoubleequals().apply(this);
    }

    public void caseANeqlCond(ANeqlCond node) {
        node.getNotequals().apply(this);
    }

    public void caseAGteqlCond(AGteqlCond node) {
        node.getGtequals().apply(this);
    }

    public void caseALteqlCond(ALteqlCond node) {
        node.getLtequals().apply(this);
    }

    public void caseAGtCond(AGtCond node) {
        node.getGt().apply(this);
    }

    public void caseALtCond(ALtCond node) {
        node.getLt().apply(this);
    }

    /*****************************************
    * END CONDITION AREA                     *
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

    public void caseTTrue(TTrue node) {
        flapjacks.push("1");
    }

    public void caseTFalse(TFalse node) {
        flapjacks.push("0");
    }

    public void caseTDoubleequals(TDoubleequals node) {
        flapjacks.push("seq");
    }

    public void caseTNotequals(TNotequals node) {
        flapjacks.push("sne");
    }

    public void caseTGtequals(TGtequals node) {
        flapjacks.push("sge");
    }

    public void caseTLtequals(TLtequals node) {
        flapjacks.push("sle");
    }

    public void caseTGt(TGt node) {
        flapjacks.push("sgt");
    }

    public void caseTLt(TLt node) {
        flapjacks.push("slt");
    }

    public void caseTBool(TBool node) {
        flapjacks.push(node.getText());
    }

    public void caseTString(TString node) {
        flapjacks.push(node.getText());
    }

    public void caseTReal(TReal node) {
        flapjacks.push(node.getText());
    }

    public void caseTInt(TInt node) {
        flapjacks.push(node.getText());
    }

    public void caseTPlus(TPlus node) {
        flapjacks.push("add");
    }

    public void caseTMinus(TMinus node) {
        flapjacks.push("sub");
    }

    public void caseTTimes(TTimes node) {
        flapjacks.push("mul");
    }

    public void caseTDivide(TDivide node) {
        flapjacks.push("div");
    }
}