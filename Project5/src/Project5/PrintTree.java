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
        //node.getClassmethodstmts().apply(this);
    }

    public void caseAIdtail(AIdtail node) {
        //Handle id tail
        //node.getId().apply(this);
    }

    /*****************************************
    * START STMT AREA                        *
    *****************************************/
    public void caseAAssignexpStmt(AAssignexpStmt node) {

    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {

    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {

    }

    public void caseAIfstmtStmt(AIfstmtStmt node) {

    }

    public void caseAWhilestmtStmt(AWhilestmtStmt node) {

    }

    public void caseAForstmtStmt(AForstmtStmt node) {

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

    public void caseAReturnstmtStmt(AReturnstmtStmt node) {

    }

    public void caseAAssignbooleanStmt(AAssignbooleanStmt node) {

    }

    public void caseASwitchstmtStmt(ASwitchstmtStmt node) {

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
    * START TOKEN AREA                       *
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