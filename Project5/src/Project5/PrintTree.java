package Project5;

import Project5.analysis.*;
import Project5.node.*;
import java.util.*;


class PrintTree extends DepthFirstAdapter
{
    private HashMap<String, Object> symbolTable = new HashMap<>();

 	public PrintTree() {
		System.out.println("Start of the Printing Action");
	}

    public void caseAProg(AProg node) {
        node.getClassmethodstmts().apply(this);
    }

    public void caseAIdtail(AIdtail node) {
        //Handle id tail
        node.getId().apply(this);
    }

    /*****************************************
    * START STMT AREA                        *
    *****************************************/
    public void caseAAssignexpStmt(AAssignexpStmt node) {
        node.getId().apply(this);
        //Handle subset here
        node.getExpr().apply(this);

        //Send values to symbol table
    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {
        node.getId().apply(this);
        //Handle subset here
        node.getStringlit().apply(this);

        //Send values to symbol table
    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {
        node.getId().apply(this);
        node.getIdtail().apply(this);
        //Put empty value in symbol table
    }

    public void caseAIfstmtStmt(AIfstmtStmt node) {
        node.getIdbool().apply(this);
    }

    /*****************************************
    * END STMT AREA                        *
    *****************************************/

    public void caseTId(TId node) {

    }

    public void caseTIntnum(TIntnum node) {

    }

    public void caseTRealnum(TRealnum node) {

    }

    public void caseTStringlit(TStringlit node) {

    }

    public void caseTSubset(TSubset node) {

    }

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