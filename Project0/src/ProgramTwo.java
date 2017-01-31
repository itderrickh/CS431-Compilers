package Starter;

public class ProgramTwo {
    //the syntax tree representation of:  echo(34)
	//You should create separate programs for each tree you create.
    private static final NumExp TWENTY = new NumExp(20);
    private static final NumExp TEN = new NumExp(10);
    private static final NumExp FIVE = new NumExp(5);
    private static final NumExp FOUR = new NumExp(4);
    private static final NumExp SIX = new NumExp(6);
    private static final VariableExp VAR_TWO = new VariableExp("two");
    private static final VariableExp VAR_THREE = new VariableExp("three");

    private static Stmt lOne = new AssignStmt("two", new ArithExp(new ArithExp(TWENTY, "-", TEN), "*", FIVE));
    private static Stmt lTwo = new AssignStmt("three", new ArithExp(new ArithExp(VAR_TWO, "%", FOUR), "+", SIX));
    private static Stmt print = new PrintStmt(new LastExpList(VAR_TWO));
    private static Stmt printTwo = new PrintStmt(new LastExpList(VAR_THREE));
    private static StmtList program = new StmtList(lOne, lTwo, print, printTwo);

	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program Two...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 34.
	    interpreter.interpret(program);

        //interpreter.interpret(lOne);
        //interpreter.interpret(lTwo);
        //interpreter.interpret(print);
        //interpreter.interpret(printTwo);
	}
}