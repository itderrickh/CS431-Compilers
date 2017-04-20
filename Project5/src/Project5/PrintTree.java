package Project5;

import Project5.analysis.*;
import Project5.node.*;
import java.util.*;


class PrintTree extends DepthFirstAdapter
{
    private HashMap<String, Object> symbolTable = new HashMap<>();
    private Stack<Object> flapjacks = new Stack<Object>();
    private SymbolTable symbolTable;
    private StringBuilder mipsString;
    private StringBuilder data;

    private int nextRegister = 0;

 	public PrintTree() {
		System.out.println("Start of the Printing Action");
        this.symbolTable = new SymbolTable();
        mipsString = new StringBuilder();
	}

    public void caseAProg(AProg node) {
        mipsString.add(".text")
        mipsString.add(".globl main")
        node.getClassmethodstmts().apply(this);

        data.add(/*Anything in the symbol table*/);

        //Add all the string builders together and store in file
    }

    public void caseAMorestatementsClassmethodstmts(AMorestatementsClassmethodstmts node) {
        node.getClassmethodstmts().apply(this);
        node.getClassmethodstmt().apply(this);
    }

    public void caseAFunctiondefClassmethodstmt(AFunctiondefClassmethodstmt node) {
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
        mipsString.add("li ");
        node.getId().apply(this);
        mipsString.add(",");
        node.getExpr().apply(this);

        symbolTable.add(id, new Symbol(id, result));
    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {

    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {
        node.getId().apply(this);
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

    }

    public void caseAIncrementStmt(AIncrementStmt node) {

    }

    public void caseADecrementStmt(ADecrementStmt node) {

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
        return node.getTerm().apply(this);
    }

    /*****************************************
    * END EXPR AREA                          *
    *****************************************/

    /*****************************************
    * START Term AREA                        *
    *****************************************/

    public void caseAFactorTerm(AFactorTerm node) {
        return node.getFactor().apply(this);
    }

    /*****************************************
    * END TERM AREA                          *
    *****************************************/

    /*****************************************
    * START FACTOR AREA                        *
    *****************************************/

    public void caseAIntegerFactor(AIntegerFactor node) {
        return node.getIntnum().apply(this);
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

    public void caseTSubset(TSubset node) {

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