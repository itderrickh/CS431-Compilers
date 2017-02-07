package Starter;

public class ProgramOne {
	//the syntax tree representation of:  one <-- 30; echo(one)
	private static IdExp VAR_ONE = new IdExp("one");
    private static Stmt ASSIGN_ONE = new AssignStmt(VAR_ONE, new NumExp(30));
    private static Stmt PRINT = new PrintStmt(new LastExpList(VAR_ONE));

	//Final program
    private static Stmt program = new Stmts(ASSIGN_ONE, new Stmts(PRINT));

	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program One...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 30.
	    interpreter.interpret(program);
	}
}
