package Starter;

public class ProgramTwo {
	//the syntax tree representation of:  two <-- 20 - 10 * 5; three <-- two % 4 + 6; echo(two, three)
	private static final NumExp TWENTY = new NumExp(20);
    private static final NumExp TEN = new NumExp(10);
    private static final NumExp FIVE = new NumExp(5);
    private static final NumExp FOUR = new NumExp(4);
    private static final NumExp SIX = new NumExp(6);
	private static final IdExp VAR_TWO = new IdExp("two");
    private static final IdExp VAR_THREE = new IdExp("three");
	private static ArithExp TWENTY_MINUS_TEN = new ArithExp(TWENTY, "-", TEN);
	private static ArithExp VAR_TWO_MOD_FOUR = new ArithExp(VAR_TWO, "%", FOUR);

	private static Stmt ASSIGN_TWO = new AssignStmt("two", new ArithExp(TWENTY_MINUS_TEN, "*", FIVE));
	private static Stmt ASSIGN_THREE = new AssignStmt("three", new ArithExp(VAR_TWO_MOD_FOUR, "+", SIX));
	private static Stmt PRINT_TWO_THREE = new PrintStmt(new MultipleExpressions(VAR_TWO, new LastExpList(VAR_THREE)));

	//Final program
	private static Stmts program = new Stmts(ASSIGN_TWO, new Stmts(ASSIGN_THREE, new Stmts(PRINT_TWO_THREE)));

	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program Two...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 50 and 8.
	    interpreter.interpret(program);
	}
}
