package Starter;

/*
	You will need to add many more classes to this file to get the interpreter 
	to work. The pattern shown below for the simple example should be enough 
	to show you what to do for the remaining classes.
*/

abstract class Stmt {}

class Stmts extends Stmt {
    public Stmt head;
    public Stmts tail;
    public Stmts(Stmt h) {
        this.head = h;
        this.tail = null;
    }
    public Stmts(Stmt h, Stmts t) {
        this.head = h;
        this.tail = t;
    }
}

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

class IdExp extends Expression
{
    public String id;
    public IdExp(String id){
        this.id = id;
    }
}

class ArithExp extends Expression
{
    public Expression left;
    public String operand;
    public Expression right;
    public ArithExp(Expression l, String o, Expression r) {
        left = l;
        operand = o;
        right = r;
    }
}

class UnaryExp extends Expression
{
    public Expression left;
    public String operand;
    public UnaryExp(Expression l, String o) {
        left = l;
        operand = o;
    }
}

abstract class ExpList {}

class MultipleExpressions extends ExpList
{
    public ExpList expList;
    public Expression exp;
    public MultipleExpressions(Expression e, ExpList el) {
        exp = e;
        expList = el;
    }
}

class LastExpList extends ExpList
{
    public Expression head;
    public LastExpList(Expression h){
        head = h;
    }
}