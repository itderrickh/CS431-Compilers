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
    private int nextRegister = 0;
    private int ifLabelCounter = 0;
    private int forLabelCounter = 0;
    private int whileLabelCounter = 0;
    private int methodLabelCounter = 0;
    private int mainBodyCounter = 0;

 	public PrintTree() {
        this.symbolTable = new SymbolTable();
        mipsString = new StringBuilder();
        data = new StringBuilder();
	}

    public String incrementRegister() {
        String next = "$t" + this.nextRegister;
        //Loop back once we hit 7
        if(this.nextRegister >= 7) {
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
                        data.append(s).append(": ").append(".double 0");
                    }
                } else if(value.getValue() instanceof String) {
                    data.append(s).append(": ").append(".asciiz ").append(value.getValue().toString());
                } else if(value.getValue() instanceof Double) {
                    data.append(s).append(": ").append(".double ").append(value.getValue().toString());
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

    /*****************************************
    * START STMT AREA                        *
    *****************************************/
    public void caseAAssignexpStmt(AAssignexpStmt node) {
        node.getId().apply(this);
        node.getExpr().apply(this);
        String updateRegister = "";
        if (flapjacks.size() >= 3) {
            updateRegister = flapjacks.pop().toString();
        }

        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();
        String type = "";
        if(value instanceof Integer) {
            type = "INT";
        } else if(value instanceof Double) {
            type = "REAL";
        }

        //Check the value to make sure the old type still is legitimate
        Symbol s = symbolTable.getValue(id);
        s.setValue(value);
        symbolTable.update(id, s);
        
        if (!updateRegister.equals("")) {
            mipsString.append("\tsw ").append(updateRegister).append(", ").append(id).append("\n");
        }
    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {
        node.getId().apply(this);
        node.getStringlit().apply(this);
        String value = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        Symbol s = symbolTable.getValue(id);
        s.setValue(value);
        symbolTable.update(id, s);

        //Check the value to make sure the old type still is legitimate
        symbolTable.update(id, s);
    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {
        node.getId().apply(this);
        node.getType().apply(this);
        String type = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        symbolTable.add(id, new Symbol(id, null, type));
    }

    public void caseAIfStmt(AIfStmt node) {
        String ifStmt = "if" + this.ifLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append(" 1, ").append(ifStmt).append("\n");
        mipsString.append("\tbeq ").append(value).append(", ").append(" 0, ").append(bodyPart).append("\n");

        mipsString.append(ifStmt).append(":\n");
        node.getStmtseq().apply(this);
        mipsString.append("\tj ").append(bodyPart).append("\n");
        mipsString.append(bodyPart).append(": \n");

        this.ifLabelCounter++;
        this.mainBodyCounter++;
    }

    public void caseAWhileStmt(AWhileStmt node) {
        String whileStmt = "while" + this.whileLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append(" 1, ").append(whileStmt).append("\n");
        mipsString.append("\tbeq ").append(value).append(", ").append(" 0, ").append(bodyPart).append("\n");

        mipsString.append(whileStmt).append(":\n");
        
        node.getStmtseq().apply(this);

        //Repeating this might fix our issue of not having the correct register
        node.getIdbool().apply(this);
        value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append(" 1, ").append(whileStmt).append("\n");

        mipsString.append("\tj ").append(bodyPart).append("\n");
        mipsString.append(bodyPart).append(": \n");

        this.whileLabelCounter++;
        this.mainBodyCounter++;
    }

    public void caseAIncrementStmtexprtail(AIncrementStmtexprtail node) {
        String currReg = this.incrementRegister();

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
        mipsString.append("\tadd ").append(currReg).append(", ").append(currReg).append(", 1\n");
        mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
    }

    public void caseADecrementStmtexprtail(ADecrementStmtexprtail node) {
        String currReg = this.incrementRegister();

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
        mipsString.append("\tsub ").append(currReg).append(", ").append(currReg).append(", 1\n");
        mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
    }

    public void caseAAssignStmtexprtail(ADecrementStmtexprtail node) {

        //TODO: handle this
        node.getId().apply(this);
        node.getExpr().apply(this);
    }

    public void caseAForStmt(AForStmt node) {
        node.getId().apply(this);
        node.getExpr().apply(this);
        //Do some assignment work here

        node.getBoolean().apply(this);
        node.getStmtexprtail().apply(this);

        node.getStmtseq().apply(this);
    }

    public void caseAGetcommandStmt(AGetcommandStmt node) {
        //Check the type of the id
        String currReg = this.incrementRegister();
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol sym = this.symbolTable.getValue(id);

        if(sym.getType().equals("INT")) {
            mipsString.append("\taddi $v0, $zero, 5\n\tsyscall\n");
            mipsString.append("\tadd ").append(currReg).append(", $zero, $v0\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(id).append("\n");
        }    
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
            mipsString.append("\tmov.s $f12, ").append(id).append("\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("BOOLEAN")) {
            mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
            mipsString.append("\tli $v0, 1\n").append("\tadd $a0, ").append(currReg).append(", $zero\n");
            mipsString.append("\tsyscall\n");
        }

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

    public void caseAAssignbooleanStmt(AAssignbooleanStmt node) {
        node.getId().apply(this);
        node.getBoolean().apply(this);
        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();

        //Check the value to make sure the old type still is legitimate
        symbolTable.update(id, new Symbol(id, value.toString(), "BOOLEAN"));
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

    public void caseAVarIdbool(AVarIdbool node) {
        node.getId().apply(this);
    }

    public void caseABoolIdbool(ABoolIdbool node) {
        node.getBoolean().apply(this);
    }

    public void caseATrueBoolean(ATrueBoolean node) {
        flapjacks.push(1);
    }

    public void caseAFalseBoolean(AFalseBoolean node) {
        flapjacks.push(0);
    }

    public void caseACondBoolean(ACondBoolean node) {
        node.getExpr().apply(this);
        node.getCond().apply(this);
        node.getRight().apply(this);
        Object rightExp = flapjacks.pop();
        String cond = flapjacks.pop().toString();
        Object leftExp = flapjacks.pop();

        String reg1 = this.incrementRegister();
        String reg2 = this.incrementRegister();
        String reg3 = this.incrementRegister();
        //Load left and right into registers
        //Compare them into a new register, push register onto stack
        if(leftExp instanceof Integer) {
            mipsString.append("\tli ").append(reg1).append(", ").append(leftExp).append("\n");
        } else if(leftExp instanceof Double) {

        } else if(leftExp instanceof String) {
            //Might have to handle id vs string here
            mipsString.append("\tmove ").append(reg1).append(", ").append(leftExp).append("\n");
        }

        if(rightExp instanceof Integer) {
            mipsString.append("\tli ").append(reg2).append(", ").append(rightExp).append("\n");
        } else if(rightExp instanceof Double) {

        } else if(rightExp instanceof String) {
            //Might have to handle id vs string here
            mipsString.append("\tmove ").append(reg2).append(", ").append(rightExp).append("\n");
        }

        mipsString.append("\t").append(cond).append(" ").append(reg3).append(", ").append(reg1).append(", ").append(reg2).append("\n");
        flapjacks.push(reg3);
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
        String nextRegister = this.incrementRegister();
        mipsString.append("\tadd " + nextRegister + ", ");
        mipsString.append(nextRegister + ", ");
        mipsString.append(leftExpr.toString() + "\n");

        mipsString.append("\t" + addOp + " " + nextRegister + ", ");
        mipsString.append(nextRegister + ", ");
        mipsString.append(rightExpr.toString() + "\n");
        flapjacks.push(null);
        flapjacks.push(nextRegister);
    }

    public void caseAPlusAddop(APlusAddop node) {
        node.getPlus().apply(this);
    }

    public void caseAMinusAddop(AMinusAddop node) {
        node.getMinus().apply(this);
    }

    public void caseAMultiplyMultop(AMultiplyMultop node) {
        node.getTimes().apply(this);
    }

    public void caseADivideMultop(ADivideMultop node) {
        node.getDivide().apply(this);
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

    public void caseAMultopTerm(AMultopTerm node) {
        node.getTerm().apply(this);
        node.getMultop().apply(this);
        node.getFactor().apply(this);
        Object rightFactor = flapjacks.pop();
        String multOp = flapjacks.pop().toString();
        Object leftTerm = flapjacks.pop();
        String nextRegister = this.incrementRegister();
        mipsString.append("\tadd " + nextRegister + ", ");
        mipsString.append(nextRegister + ", ");
        mipsString.append(leftTerm.toString() + "\n");

        mipsString.append("\t" + multOp + " ");
        mipsString.append(nextRegister + ", ");
        mipsString.append(rightFactor.toString() + "\n");

        //only move the lower register, because nobody needs more than 32 bits
        mipsString.append("\tmflo "+ nextRegister + "\n");
        flapjacks.push(null);
        flapjacks.push(nextRegister);
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
        node.getRealnum().apply(this);
    }

    public void caseAIdFactor(AIdFactor node) {
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        String newRegister = "$t" + this.nextRegister;
        mipsString.append("\tlw " + newRegister + " " + id + "\n");
        flapjacks.push(newRegister);
        nextRegister += 1;
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
        flapjacks.push("mult");
    }

    public void caseTDivide(TDivide node) {
        flapjacks.push("div");
    }
}