package Project3.interpreter;

public class Interpret {
	public static void main(String[] args) {
		//Create a new Interpreter Object
	    Interpreter interpreter = new Interpreter();
	    System.out.println("Evaluating Program One...");
	    //Call the overloaded interpret method with the
	    //static program created above. Should print out 30.
	    interpreter.interpret(ProgExpr.program);
	}
}