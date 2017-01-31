package Starter;

/*
	Add methods to handle the traversal of other nodes in 
	the syntax tree. Some methods will need to be updated. 
	For instance, the interpret method for a Stmt assumes 
	that all statements are print statements. This is 
	obviously not the case and needs to be handled.
*/

public class Interpreter{

	//currently assumes all Stmt are PrintStmt
	//probably needs to be updated
 	public int interpret(Stmt stm)  {
    	return this.interpret((PrintStmt)stm);
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
    	return 0;
 	}

 	public int interpret(NumExp exp) {
    	return exp.num;
 	}

 	public int interpret(ExpList list) {
    	return this.interpret((LastExpList)list);
 	}

 	public int interpret(LastExpList list) {
    	return this.interpret(list.head);
  	}
}
