package Starter;

public class ProgramThree {
	//the syntax tree representation of:
	//	four <-- 5 + 10;
	//	five <-- 10 / 3;
	//	echo(four, five);
	//	four <-- four>>;
	//	echo(four);
	//	five <-- four<< / four>>;
	//	echo(four,five)
  	private static final NumExp FIVE = new NumExp(5);
    private static final NumExp TEN = new NumExp(10);
    private static final NumExp THREE = new NumExp(3);

	private static final IdExp VAR_FOUR = new IdExp("four");
    private static final IdExp VAR_FIVE = new IdExp("five");
	private static ArithExp FIVE_PLUS_TEN = new ArithExp(FIVE, "+", TEN);
	private static ArithExp TEN_DIVIDE_THREE = new ArithExp(TEN, "/", THREE);

	private static Stmt ASSIGN_FOUR = new AssignStmt(VAR_FOUR, FIVE_PLUS_TEN);
	private static Stmt ASSIGN_FIVE = new AssignStmt(VAR_FIVE, TEN_DIVIDE_THREE);
	private static PrintStmt PRINT_FOUR_FIVE = new PrintStmt(new MultipleExpressions(VAR_FOUR, new LastExpList(VAR_FIVE)));
	private static Stmt REASSIGN_FOUR_RS = new AssignStmt(VAR_FOUR, new UnaryExp(VAR_FOUR, ">>"));
	private static PrintStmt PRINT_FOUR = new PrintStmt(new LastExpList(VAR_FOUR));
	//Old way that was incorrect
	//private static Stmt REASSIGN_FIVE = new AssignStmt("five", new ArithExp(new UnaryExp(VAR_FOUR, "<<"), "/", new UnaryExp(VAR_FOUR, ">>")));
	private static Stmt REASSIGN_FIVE = new AssignStmt(VAR_FIVE, new UnaryExp(new ArithExp(new UnaryExp(VAR_FOUR, "<<"), "/", VAR_FOUR), ">>"));
	private static PrintStmt PRINT_FOUR_FIVE_AGAIN = new PrintStmt(new MultipleExpressions(VAR_FOUR, new LastExpList(VAR_FIVE)));

	//Final program
	private static Stmts program = new Stmts(ASSIGN_FOUR, 
									new Stmts(ASSIGN_FIVE, 
									new Stmts(PRINT_FOUR_FIVE, 
									new Stmts(REASSIGN_FOUR_RS,
									new Stmts(PRINT_FOUR,
									new Stmts(REASSIGN_FIVE,
									new Stmts(PRINT_FOUR_FIVE_AGAIN)))))));
	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program Three...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 15 3 7 7 4.
	    interpreter.interpret(program);
	}
}
