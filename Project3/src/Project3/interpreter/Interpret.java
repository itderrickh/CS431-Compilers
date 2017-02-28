package Project3.interpreter;

public class Interpret {
	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program...");
	    interpreter.interpret(ProgExpr.program);
	}

}