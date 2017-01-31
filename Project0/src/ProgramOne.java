package Starter;

public class ProgramOne {
    //the syntax tree representation of:  echo(34)
	//You should create separate programs for each tree you create.
    private static Stmt assignStmt = new AssignStmt("one", new NumExp(30));
    private static Stmt printStmt = new PrintStmt(new LastExpList(new VariableExp("one")));
    private static StmtList program = new StmtList(assignStmt, printStmt);

	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program One...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 30.
	    interpreter.interpret(program);

		//interpreter.interpret(assignStmt);
		//interpreter.interpret(printStmt);
	}
}