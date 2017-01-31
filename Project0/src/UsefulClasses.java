package Starter;

/*
	You will need to add many more classes to this file to get the interpreter 
	to work. The pattern shown below for the simple example should be enough 
	to show you what to do for the remaining classes.
*/

abstract class Stmt {}

//handles the Stmt --> echo ( ExpList ) production
class PrintStmt extends Stmt{
    public ExpList exps;
    public PrintStmt(ExpList e){
        exps = e;
    }
}

//handles the Stmt --> id <-- Expression production
class AssignStmt extends Stmt{
    public String id;
    public Expression exp;
    public AssignStmt(String i, Expression e){
        id = i;
        exp = e;
    }
}

abstract class Expression {}

class NumExp extends Expression
{
    public int num;
    public NumExp(int n){
        num = n;
    }
}

abstract class ExpList {}

class LastExpList extends ExpList
{
    public Expression head;
    public LastExpList(Expression h){
        head = h;
    }
}