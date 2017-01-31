package Starter;

public class ProgramThree {
	//the syntax tree representation of:  echo(34)
	//You should create separate programs for each tree you create.
    private static VariableExp VAR_FOUR = new VariableExp("four");
    private static VariableExp VAR_FIVE = new VariableExp("five");
    private static NumExp THREE = new NumExp(3);
    private static NumExp FIVE = new NumExp(5);
    private static NumExp TEN = new NumExp(10);

    private static AssignStmt ASSIGN_VAR_FOUR = new AssignStmt("four", new ArithExp(FIVE, "+", TEN));
    private static AssignStmt ASSIGN_VAR_FIVE = new AssignStmt("five", new ArithExp(TEN, "/", THREE));
    private static StmtList PRINT_FOUR_FIVE = new StmtList(new PrintStmt(new LastExpList(VAR_FOUR)), new PrintStmt(new LastExpList(VAR_FIVE)));

    private static StmtList program = new StmtList(ASSIGN_VAR_FOUR, ASSIGN_VAR_FIVE, PRINT_FOUR_FIVE);

  	//private static StmtList program = new PrintStmt(new LastExpList(new NumExp(34)));

	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program Three...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 34.
	    interpreter.interpret(program);
	}
}
