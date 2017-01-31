package Starter;

/*
	Add methods to handle the traversal of other nodes in 
	the syntax tree. Some methods will need to be updated. 
	For instance, the interpret method for a Stmt assumes 
	that all statements are print statements. This is 
	obviously not the case and needs to be handled.
*/
import java.util.HashMap;

public class Interpreter{
	private HashMap<String, Expression> map = new HashMap<>();

	//currently assumes all Stmt are PrintStmt
	//probably needs to be updated
 	public int interpret(Stmt stm)  {
		if(stm instanceof StmtList) {
			StmtList lst = (StmtList) stm;
			for(Stmt s : lst.stmts) {
				this.interpret(s);
			}

			return 0;
		} else if(stm instanceof PrintStmt) {
			return this.interpret((PrintStmt)stm);
		} else if(stm instanceof AssignStmt) {
			return this.interpret((AssignStmt)stm);
		}

		return 0;
 	}

	public int interpret(AssignStmt stm) {
		map.put(stm.id, stm.exp);
		return 0;
	}

	//each PrintStmt contains an ExpList
	//evaluate the ExpList
 	public int interpret(PrintStmt stm) {
 		ExpList exp = stm.exps;
 	   	System.out.println(this.interpret(exp));
 	   	return 0;
 	}

 	public int interpret(Expression exp) {
    	if (exp instanceof NumExp)
      		return this.interpret((NumExp)exp);
		if (exp instanceof VariableExp)
			return this.interpret((VariableExp)exp);
		if (exp instanceof ArithExp)
			return this.interpret((ArithExp)exp);
    	return 0;
 	}

 	public int interpret(NumExp exp) {
    	return exp.num;
 	}

	public int interpret(VariableExp exp) {
		return this.interpret(map.get(exp.id));
	}

	public int interpret(ArithExp exp) {
		if(exp.operand.equals("*")) {
			return this.interpret(exp.left) * this.interpret(exp.right);
		} else if(exp.operand.equals("/")) {
			return this.interpret(exp.left) / this.interpret(exp.right);
		} else if(exp.operand.equals("+")) {
			return this.interpret(exp.left) + this.interpret(exp.right);
		} else if(exp.operand.equals("-")) {
			return this.interpret(exp.left) - this.interpret(exp.right);
		} else if(exp.operand.equals("%")) {
			return this.interpret(exp.left) % this.interpret(exp.right);
		}

		return 0;
	}

 	public int interpret(ExpList list) {
    	return this.interpret((LastExpList)list);
 	}

 	public int interpret(LastExpList list) {
    	return this.interpret(list.head);
  	}
}
