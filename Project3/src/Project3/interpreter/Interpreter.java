package Project3.interpreter;

/*
	Add methods to handle the traversal of other nodes in 
	the syntax tree. Some methods will need to be updated. 
	For instance, the interpret method for a Stmt assumes 
	that all statements are print statements. This is 
	obviously not the case and needs to be handled.
*/

import java.util.HashMap;
import java.lang.*;

public class Interpreter{
	private HashMap<IdExp, Expression> symbols = new HashMap<>();

	//currently assumes all Stmt are PrintStmt
	//probably needs to be updated
 	public int interpret(Stmt stm)  {
		if(stm instanceof Stmts) {
			return this.interpret((Stmts) stm);
		} else if(stm instanceof PrintStmt) {
			return this.interpret((PrintStmt)stm);
		} else if(stm instanceof AssignStmt) {
			return this.interpret((AssignStmt)stm);
		}
    	
		return 0;
 	}

	public int interpret(Stmts stm) {
		this.interpret(stm.head);
		if(stm.tail != null) {
			this.interpret(stm.tail);
		}

		return 0;
	}

	//each PrintStmt contains an ExpList
	//evaluate the ExpList
 	public int interpret(PrintStmt stm) {
 		ExpList exp = stm.exps;
		
		if(exp instanceof Exps) {
			System.out.println(this.interpret((Exps)exp));
		}

 	   	return 0;
 	}

	 public int interpret(AssignStmt stm) {
		if(symbols.containsKey(stm.id)) {
			NumExp newExp = new NumExp(this.interpret(stm.exp));
			symbols.replace(stm.id, newExp);
		} else {
			symbols.put(stm.id, stm.exp);
		}
 		
 	   	return 0;
 	}

 	public int interpret(Expression exp) {
    	if (exp instanceof NumExp)
      		return this.interpret((NumExp)exp);
		if (exp instanceof IdExp)
			return this.interpret((IdExp)exp);
		if (exp instanceof ArithExp)
			return this.interpret((ArithExp)exp);
		if (exp instanceof UnaryExp)
			return this.interpret((UnaryExp)exp);
    	return 0;
 	}

 	public int interpret(NumExp exp) {
    	return exp.num;
 	}

	public int interpret(IdExp exp) {
		return this.interpret(symbols.get(exp));
	}

	public int interpret(ArithExp exp) {
		if(exp.operand == "*") {
			return this.interpret(exp.right) * this.interpret(exp.left);
		} else if(exp.operand == "/") {
			return this.interpret(exp.right) / this.interpret(exp.left);
		} else if(exp.operand == "+") {
			return this.interpret(exp.right) + this.interpret(exp.left);
		} else if(exp.operand == "-") {
			return this.interpret(exp.right) - this.interpret(exp.left);
		} else if(exp.operand == "%") {
			return this.interpret(exp.right) % this.interpret(exp.left);
		}

		return 0;
	}

	public int interpret(UnaryExp exp) {
		if(exp.operand == ">>") {
			return this.interpret(exp.left) >> 1;
		} else if(exp.operand == "<<") {
			return this.interpret(exp.left) << 1;
		}

		return 0;
	}

	public String interpret(Exps list) {
		if(list.expList == null) {
			return this.interpret(list.exp) + "";
		} else {
			String result = "";
			result += this.interpret(list.exp);

			if(list.expList instanceof Exps) {
				return result + "\n" + this.interpret((Exps)list.expList);
			}

			return result;	
		}
	}
}