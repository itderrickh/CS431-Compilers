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
        symbolTable.update(id, new Symbol(id, value));
    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {

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

    }

    public void caseAPutcommandStmt(APutcommandStmt node) {
        String currReg = "$t" + this.nextRegister;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();

        mipsString.append("\tlw ").append(currReg).append(", ").append(id).append("\n");
        //Note check type here
        mipsString.append("\tli $v0 1\n").append("\tadd $a0, ").append(currReg).append(", $zero\n");
        mipsString.append("\tsyscall\n");

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

    /*****************************************
    * END TOKEN AREA                         *
    *****************************************/


	//this gets called if the production is prog --> id digit
    //public void caseAFirstProg(AFirstProg node){
    //    System.out.println("\tGot a first prog!");
    //}

	//prog --> lotnumbers
    //public void caseASecondProg(ASecondProg node){
    //    System.out.println("\tGot a second prog!");
    //    node.getLotnumbers().apply(this);
    //}

	//prog --> id id digit digit
    //public void caseAThirdProg(AThirdProg node){
    //    System.out.println("\tGot a third prog!");
    //    node.getEachsymbolisuniqueinaproduction().apply(this);
    //    node.getSecondid().apply(this);
    //    node.getDigitone().apply(this);
    //    node.getDigittwo().apply(this);
    //}

	//if it reaches an id, print it off
    //public void caseTId(TId node){
	//	 System.out.println("\tGot myself an id: <"+node.getText()+">");
	//}

	//if it reaches a digit, print it off
    //public void caseTDigit(TDigit node){
	//	 System.out.println("\tGot myself a digit: <"+node.getText()+">");
	//}

	//if it reaches a ALotnumbers, print off the digit stored inside of it
	//public void caseALotnumbers(ALotnumbers node){
	//	System.out.println("\tPrinting the first number: "+node.getDigit());
	//}
}