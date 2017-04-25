package Project5;

import Project5.symbols.*;
import Project5.analysis.*;
import Project5.node.*;
import java.util.*;


class PrintTree extends DepthFirstAdapter
{
    private Stack<Object> flapjacks = new Stack<Object>();
    private StringBuilder mipsString;
    private StringBuilder data;
    private ITable symbolTable;

    private StringBuilder errors;
    private int nextRegister = 0;
    private int ifLabelCounter = 0;
    private int elseLabelCounter = 0;
    private int forLabelCounter = 0;
    private int whileLabelCounter = 0;
    private int methodLabelCounter = 0;
    private int caseLabelCounter = 0;
    private int defaultLabelCounter = 0;
    private int mainBodyCounter = 0;
    private int floatDataLabelCounter = 0;
    private int variableCounter = 0;

 	public PrintTree() {
        this.symbolTable = (ITable)new GlobalTable();
        mipsString = new StringBuilder();
        data = new StringBuilder();
        errors = new StringBuilder();
	}

    public void addScope(String id) {
        if(symbolTable instanceof GlobalTable) {
            GlobalTable table = (GlobalTable) symbolTable;
            ClassTable newTable = new ClassTable();
            newTable.setParent((ITable)table);
            table.globalClasses.put(id, newTable);
        } else if(symbolTable instanceof ClassTable) {
            ClassTable table = (ClassTable) symbolTable;
            MethodTable newTable = new MethodTable();
            newTable.setParent((ITable)table);
            table.classFunctions.put(id, newTable);
        } else if(symbolTable instanceof MethodTable) {
            MethodTable table = (MethodTable) symbolTable;
            VariableTable newTable = new VariableTable();
            newTable.setParent((ITable)table);
            table.innerScopes.put(id, newTable);
        }
    }

    public void addToSymbolTable(String id, Symbol sym) {
        if(symbolTable instanceof GlobalTable) {
            GlobalTable table = (GlobalTable) symbolTable;
            if(table.globalVariables.containsKey(id)) {
                Symbol s = table.globalVariables.getValue(id);
                if(!s.getType().equals(sym.getType())) {
                    this.errors.append("Invalid type exception: ")
                        .append(sym.getId())
                        .append(" is type ")
                        .append(s.getType())
                        .append(" but was assigned type ")
                        .append(sym.getType())
                        .append("\n");
                }
                table.globalVariables.update(id, sym);
            } else {
                table.globalVariables.add(id, sym);
            }
        } else if(symbolTable instanceof ClassTable) {
            ClassTable table = (ClassTable) symbolTable;
            if(table.classVariables.containsKey(id)) {
                Symbol s = table.classVariables.getValue(id);
                if(!s.getType().equals(sym.getType())) {
                    this.errors.append("Invalid type exception: ")
                        .append(sym.getId())
                        .append(" is type ")
                        .append(s.getType())
                        .append(" but was assigned type ")
                        .append(sym.getType())
                        .append("\n");
                }
                table.classVariables.update(id, sym);
            } else {
                table.classVariables.add(id, sym);
            }
        } else if(symbolTable instanceof MethodTable) {
            MethodTable table = (MethodTable) symbolTable;
            if(table.methodVariables.containsKey(id)) {
                Symbol s = table.methodVariables.getValue(id);
                if(!s.getType().equals(sym.getType())) {
                    this.errors.append("Invalid type exception: ")
                        .append(sym.getId())
                        .append(" is type ")
                        .append(s.getType())
                        .append(" but was assigned type ")
                        .append(sym.getType())
                        .append("\n");
                }
                table.methodVariables.update(id, sym);
            } else {
                table.methodVariables.add(id, sym);
            }
        } else if(symbolTable instanceof VariableTable) {
            VariableTable table = (VariableTable) symbolTable;
            if(table.innerVariables.containsKey(id)) {
                Symbol s = table.innerVariables.getValue(id);
                if(!s.getType().equals(sym.getType())) {
                    this.errors.append("Invalid type exception: ")
                        .append(sym.getId())
                        .append(" is type ")
                        .append(s.getType())
                        .append(" but was assigned type ")
                        .append(sym.getType())
                        .append("\n");
                }
                table.innerVariables.update(id, sym);
            } else {
                table.innerVariables.add(id, sym);
            }
        }
    }

    public void changeScope(Boolean deeper, String id) {
        if(deeper) {
            addScope(id);
            if(symbolTable instanceof GlobalTable) {
                GlobalTable table = (GlobalTable) symbolTable;
                this.symbolTable = (ITable)table.globalClasses.get(id);
            } else if(symbolTable instanceof ClassTable) {
                ClassTable table = (ClassTable) symbolTable;
                this.symbolTable = (ITable)table.classFunctions.get(id);
            } else if(symbolTable instanceof MethodTable) {
                MethodTable table = (MethodTable) symbolTable;
                this.symbolTable = (ITable)table.innerScopes.get(id);
            }
        } else {
            this.symbolTable = this.symbolTable.getParent();
        }
    }

    public Symbol findInSymbolTable(ITable symbolTable, String id) {
        if(symbolTable instanceof GlobalTable) {
            GlobalTable table = (GlobalTable) symbolTable;
            if(table.globalVariables.containsKey(id)) {
                return table.globalVariables.getValue(id);
            }
        } else if(symbolTable instanceof ClassTable) {
            ClassTable table = (ClassTable) symbolTable;
            if(table.classVariables.containsKey(id)) {
                return table.classVariables.getValue(id);
            } else {
                return findInSymbolTable(table.getParent(), id);
            }
        } else if(symbolTable instanceof MethodTable) {
            MethodTable table = (MethodTable) symbolTable;
            if(table.methodVariables.containsKey(id)) {
                return table.methodVariables.getValue(id);
            } else {
                return findInSymbolTable(table.getParent(), id);
            }
        } else if(symbolTable instanceof VariableTable) {
            VariableTable table = (VariableTable) symbolTable;
            if(table.innerVariables.containsKey(id)) {
                return table.innerVariables.getValue(id);
            } else {
                return findInSymbolTable(table.getParent(), id);
            }
        }

        return null;
    }

    public void doSymbol(Symbol s) {
        if(s != null) {
            data.append("\t");

            if(s.getValue() == null) {
                //Set the variable to the default value
                if(s.getType().equals("INT")) {
                    data.append(s.getId()).append(": ").append(".word 0");
                } else if(s.getType().equals("BOOLEAN")) {
                    data.append(s.getId()).append(": ").append(".word 0");
                } else if(s.getType().equals("STRING")) {
                    data.append(s.getId()).append(": ").append(".asciiz \"\"");
                } else if(s.getType().equals("REAL")) {
                    data.append(s.getId()).append(": ").append(".float 0");
                }
            } else if(s.getValue() instanceof String) {
                data.append(s.getId()).append(": ").append(".asciiz ").append(s.getValue().toString());
            } else if(s.getValue() instanceof Double) {
                data.append(s.getId()).append(": ").append(".float ").append(s.getValue().toString());
            } else if(s.getValue() instanceof Integer) {
                data.append(s.getId()).append(": ").append(".word ").append(s.getValue().toString());
            }
            data.append("\n");
        } else {
            //Turn this into a warning later
            errors.append("Undeclared variable: " + s + '\n');
        }
    }

    public void traverseSymbols() {
        //Go to the top of the symbolTable
        while(!(this.symbolTable instanceof GlobalTable)) {
            this.symbolTable = (ITable)this.symbolTable.getParent();
        }

        GlobalTable gt = (GlobalTable)this.symbolTable;
        for(String g : gt.globalVariables.getKeys()) {
            Symbol s = gt.globalVariables.getValue(g);
            doSymbol(s);
        }

        for(String gc: gt.globalClasses.keySet()) {
            ClassTable ct = gt.globalClasses.get(gc);
            for(String c : ct.classVariables.getKeys()) {
                Symbol sy = ct.classVariables.getValue(c);
                doSymbol(sy);
            }

            for(String cg: ct.classFunctions.keySet()) {
                MethodTable mt = ct.classFunctions.get(cg);
                for(String g : mt.methodVariables.getKeys()) {
                    Symbol sym = mt.methodVariables.getValue(g);
                    doSymbol(sym);
                }

                for(String mg: mt.innerScopes.keySet()) {
                    VariableTable vt = mt.innerScopes.get(cg);
                    for(String v : vt.innerVariables.getKeys()) {
                        Symbol symb = vt.innerVariables.getValue(v);
                        doSymbol(symb);
                    }
                }
            }
        }
    }

    public String getErrors() {
        return errors.toString();
    }

    public String incrementRegister() {
        String next = "$t" + this.nextRegister;
        //Loop back once we hit 7
        if(this.nextRegister >= 7) {
            this.nextRegister = 0;
        } else {
            this.nextRegister++;
        }

        return next;
    }

    public String incrementFloatRegister() {
        String next = "$f" + this.nextRegister;
        //Loop back once we hit 11
        if(this.nextRegister >= 11) {
            this.nextRegister = 0;
        } else {
            this.nextRegister++;
        }

        return next;
    }

    public String getResult() {
        return mipsString.toString() + "\n" + data.toString();
    }

    public void caseAProg(AProg node) {
        mipsString.append(".text\n");
        mipsString.append(".globl main\n");

        node.getClassmethodstmts().apply(this);

        //Write the end of program command
        mipsString.append("\tli $v0, 10\n").append("\tsyscall\n");

        //Write the data section
        data.append(".data\n");
        data.append("\tNEWLINE: .asciiz \"\\n\"\n");
        data.append("\tFLOATONE: .float 1.0\n");
        traverseSymbols();
    }

    public void caseAMorestatementsClassmethodstmts(AMorestatementsClassmethodstmts node) {
        node.getClassmethodstmts().apply(this);
        node.getClassmethodstmt().apply(this);
    }

    public void caseAFunctiondefClassmethodstmt(AFunctiondefClassmethodstmt node) {
        node.getId().apply(this);
        String id = flapjacks.pop().toString().toLowerCase();
        mipsString.append(id).append(": \n");

        changeScope(true, id);

        node.getStmtseq().apply(this);

        changeScope(false, "");
    }

    public void caseAMorestatementsStmtseq(AMorestatementsStmtseq node) {
        node.getStmt().apply(this);
        node.getStmtseq().apply(this);
    }

    /*****************************************
    * START STMT AREA                        *
    *****************************************/
    public void caseAAssignexpStmt(AAssignexpStmt node) {
        node.getId().apply(this);
        node.getExpr().apply(this);
        String updateRegister = "";
        if (flapjacks.size() >= 3) {
            updateRegister = flapjacks.pop().toString();
        }

        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();
        String type = "";
        if(value instanceof Integer) {
            type = "INT";
        } else if(value instanceof Double) {
            type = "REAL";
        }

        Symbol s = findInSymbolTable(this.symbolTable, id);
        s.setValue(value);
        addToSymbolTable(id, s);
        
        if (!updateRegister.equals("")) {
            if (updateRegister.indexOf("$f") != -1) {
                mipsString.append("\ts.s ").append(updateRegister).append(", ").append(s.getId()).append("\n");
            }
            else {
                mipsString.append("\tsw ").append(updateRegister).append(", ").append(s.getId()).append("\n");
            }
        }
    }

    public void caseAAssignstringStmt(AAssignstringStmt node) {
        node.getId().apply(this);
        node.getStringlit().apply(this);
        String value = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        Symbol s = findInSymbolTable(this.symbolTable, id);
        s.setValue(value);
        addToSymbolTable(id, s);
    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {
        node.getId().apply(this);
        node.getType().apply(this);
        String type = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        addToSymbolTable(id, new Symbol("variable" + this.variableCounter, null, type));
        this.variableCounter++;
    }

    public void caseAIfStmt(AIfStmt node) {
        String ifStmt = "if" + this.ifLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(ifStmt).append("\n");
        mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(bodyPart).append("\n");

        mipsString.append(ifStmt).append(":\n");
        changeScope(true, ifStmt);
        node.getStmtseq().apply(this);
        changeScope(false, "");
        mipsString.append("\tj ").append(bodyPart).append("\n");
        mipsString.append(bodyPart).append(": \n");

        this.ifLabelCounter++;
        this.mainBodyCounter++;
    }

    public void caseAIfelseStmt(AIfelseStmt node) {
        String ifStmt = "if" + this.ifLabelCounter;
        String elseStmt = "else" + this.ifLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(ifStmt).append("\n");
        mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(elseStmt).append("\n");

        mipsString.append(ifStmt).append(":\n");
        changeScope(true, ifStmt);
        node.getStmtseq().apply(this);
        changeScope(false, "");
        mipsString.append("\tj ").append(bodyPart).append("\n");

        mipsString.append(elseStmt).append(":\n");
        changeScope(true, elseStmt);
        node.getStwo().apply(this);
        changeScope(false, "");
        mipsString.append("\tj ").append(bodyPart).append("\n");
        mipsString.append(bodyPart).append(": \n");

        this.ifLabelCounter++;
        this.elseLabelCounter++;
        this.mainBodyCounter++;
    }

    public void caseAWhileStmt(AWhileStmt node) {
        String whileStmt = "while" + this.whileLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(whileStmt).append("\n");
        mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(bodyPart).append("\n");

        mipsString.append(whileStmt).append(":\n");
        
        changeScope(true, whileStmt);
        node.getStmtseq().apply(this);

        //Repeating this might fix our issue of not having the correct register
        node.getIdbool().apply(this);
        value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append(" 1, ").append(whileStmt).append("\n");

        mipsString.append("\tj ").append(bodyPart).append("\n");

        changeScope(false, "");
        mipsString.append(bodyPart).append(": \n");

        this.whileLabelCounter++;
        this.mainBodyCounter++;
    }

    public void caseAIncrementStmtexprtail(AIncrementStmtexprtail node) {
        String currReg;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol symbol = findInSymbolTable(this.symbolTable, id);
        if (symbol.getType().equals("REAL")) {
            currReg = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            String oneRegister = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(oneRegister).append(", ").append("FLOATONE\n");
            mipsString.append("\tadd.s ").append(currReg).append(", ").append(currReg).append(", ").append(oneRegister).append("\n");
            mipsString.append("\ts.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
        else {
            currReg = this.incrementRegister();
            mipsString.append("\tlw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tadd ").append(currReg).append(", ").append(currReg).append(", 1\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
    }

    public void caseADecrementStmtexprtail(ADecrementStmtexprtail node) {
        String currReg;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol symbol = findInSymbolTable(this.symbolTable, id);
        if (symbol.getType().equals("REAL")) {
            currReg = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            String oneRegister = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(oneRegister).append(", ").append("FLOATONE\n");
            mipsString.append("\tsub.s ").append(currReg).append(", ").append(currReg).append(", ").append(oneRegister).append("\n");
            mipsString.append("\ts.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
        else {
            currReg = this.incrementRegister();
            mipsString.append("\tlw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tsub ").append(currReg).append(", ").append(currReg).append(", 1\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
    }

    public void caseAAssignStmtexprtail(AAssignStmtexprtail node) {

        //TODO: handle this
        node.getId().apply(this);
        node.getExpr().apply(this);
    }

    public void caseAForStmt(AForStmt node) {
        String forStmt = "for" + this.forLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;

        node.getId().apply(this);
        node.getExpr().apply(this);
        String updateRegister = "";
        if (flapjacks.size() >= 3) {
            updateRegister = flapjacks.pop().toString();
        }

        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();
        Symbol sym = findInSymbolTable(this.symbolTable, id);
        String type = "";
        if(value instanceof Integer) {
            type = "INT";
        } else if(value instanceof Double) {
            type = "REAL";
        }

        //Check the value to make sure the old type still is legitimate
        addToSymbolTable(id, new Symbol(sym.getId(), value, type));
        
        if (!updateRegister.equals("")) {
            mipsString.append("\tsw ").append(updateRegister).append(", ").append(id).append("\n");
        }

        node.getBoolean().apply(this);

        //Pop something off the stack, an id or a register
        value = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(forStmt).append("\n");
        mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(bodyPart).append("\n");

        mipsString.append(forStmt).append(":\n");
        
        changeScope(true, forStmt);
        //Do this every iteration
        node.getStmtseq().apply(this);

        //Do this every iteration
        node.getStmtexprtail().apply(this);

        //Repeating this might fix our issue of not having the correct register
        node.getBoolean().apply(this);
        String svalue = flapjacks.pop().toString();
        mipsString.append("\tbeq ").append(svalue).append(", ").append("1, ").append(forStmt).append("\n");

        mipsString.append("\tj ").append(bodyPart).append("\n");

        changeScope(false, "");
        mipsString.append(bodyPart).append(": \n");
    }

    public void caseAGetcommandStmt(AGetcommandStmt node) {
        //Check the type of the id
        String currReg = this.incrementRegister();
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol sym = findInSymbolTable(this.symbolTable, id);

        if(sym.getType().equals("INT")) {
            mipsString.append("\taddi $v0, $zero, 5\n\tsyscall\n");
            mipsString.append("\tadd ").append(currReg).append(", $zero, $v0\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(sym.getId()).append("\n");
        }

        //NEED TO HANDLE STRING AND BOOL AND REAL HERE
    }

    public void caseAPutcommandStmt(APutcommandStmt node) {
        String currReg;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol sym = findInSymbolTable(this.symbolTable, id);

        String type = sym.getType();
        if(type.equals("STRING")) {
            mipsString.append("\tli $v0, 4\n").append("\tla $a0, ").append(sym.getId()).append("\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("INT")) {
            currReg = this.incrementRegister();
            mipsString.append("\tlw ").append(currReg).append(", ").append(sym.getId()).append("\n");
            mipsString.append("\tli $v0, 1\n").append("\tadd $a0, ").append(currReg).append(", $zero\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("REAL")) {
            currReg = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(currReg).append(", ").append(sym.getId()).append("\n");
            mipsString.append("\tmov.s $f12, ").append(currReg).append("\n");
            mipsString.append("\tli $v0, 2\n");
            mipsString.append("\tsyscall\n");
        } else if(type.equals("BOOLEAN")) {
            currReg = this.incrementRegister();
            mipsString.append("\tlw ").append(currReg).append(", ").append(sym.getId()).append("\n");
            mipsString.append("\tli $v0, 1\n").append("\tadd $a0, ").append(currReg).append(", $zero\n");
            mipsString.append("\tsyscall\n");
        }

        mipsString.append("\tli $v0, 4\n").append("\tla $a0, NEWLINE\n").append("\tsyscall\n");
    }

    public void caseAIncrementStmt(AIncrementStmt node) {
        String currReg;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol symbol = findInSymbolTable(this.symbolTable, id);
        if (symbol.getType().equals("REAL")) {
            currReg = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            String oneRegister = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(oneRegister).append(", ").append("FLOATONE\n");
            mipsString.append("\tadd.s ").append(currReg).append(", ").append(currReg).append(", ").append(oneRegister).append("\n");
            mipsString.append("\ts.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
        else {
            currReg = this.incrementRegister();
            mipsString.append("\tlw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tadd ").append(currReg).append(", ").append(currReg).append(", 1\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
    }

    public void caseADecrementStmt(ADecrementStmt node) {
        String currReg;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol symbol = findInSymbolTable(this.symbolTable, id);
        if (symbol.getType().equals("REAL")) {
            currReg = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            String oneRegister = this.incrementFloatRegister();
            mipsString.append("\tl.s ").append(oneRegister).append(", ").append("FLOATONE\n");
            mipsString.append("\tsub.s ").append(currReg).append(", ").append(currReg).append(", ").append(oneRegister).append("\n");
            mipsString.append("\ts.s ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
        else {
            currReg = this.incrementRegister();
            mipsString.append("\tlw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tsub ").append(currReg).append(", ").append(currReg).append(", 1\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(symbol.getId()).append("\n");
        }
    }

    public void caseAAssignbooleanStmt(AAssignbooleanStmt node) {
        node.getId().apply(this);
        node.getBoolean().apply(this);
        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();
        Symbol sym = findInSymbolTable(this.symbolTable, id);
        sym.setValue(value);
        //Check the value to make sure the old type still is legitimate
        addToSymbolTable(id, sym);
        variableCounter++;
    }

    public void caseASwitchStmt(ASwitchStmt node) {
        //get the actual expression
        node.getExpr().apply(this);

        String caseExpr = flapjacks.pop().toString();
        //get the first case number, and load it for comparison
        node.getIntnum().apply(this);
        String caseNum = flapjacks.pop().toString();
        int caseStart = this.caseLabelCounter;
        mipsString.append("\tbeq " + caseExpr + ", " + caseNum + ", case" + caseStart + "\n");
        caseStart++;
        //handle the rest of the cases
        LinkedList<PMorecases> morecases = node.getMorecases();
        for (int moreCaseIndex = 0; moreCaseIndex < morecases.size(); moreCaseIndex++) {
            AMorecases cases = (AMorecases) morecases.get(moreCaseIndex);
            cases.getIntnum().apply(this);
            String currentCaseNum = flapjacks.pop().toString();
            mipsString.append("\tbeq " + caseExpr + ", " + currentCaseNum + ", case" + caseStart + "\n");
            caseStart++;
        }

        mipsString.append("\tj default" + this.defaultLabelCounter + "\n");

        caseStart = this.caseLabelCounter;
        mipsString.append("case" + caseStart + ":\n");
        //get the sequence for the first case
        node.getStmtseq().apply(this);
        if (node.getBreakpart() != null) {
            mipsString.append("\tj main" + this.mainBodyCounter + "\n");
        }

        caseStart++;
        for (int moreCaseIndex = 0; moreCaseIndex < morecases.size(); moreCaseIndex++) {
            mipsString.append("case" + caseStart + ":\n");
            AMorecases cases = (AMorecases) morecases.get(moreCaseIndex);
            changeScope(true, "case" + caseStart);
            cases.getStmtseq().apply(this);
            changeScope(false, "");

            if (cases.getBreakpart() != null) {
                mipsString.append("\tj main" + this.mainBodyCounter + "\n");
            }
            caseStart++;
        }
        mipsString.append("default" + this.defaultLabelCounter + ":\n");
        changeScope(true, "default" + this.defaultLabelCounter);
        node.getStwo().apply(this);
        changeScope(false, "");
        String bodyPart = "main" + this.mainBodyCounter;
        mipsString.append(bodyPart + ":\n");
        this.mainBodyCounter++;
        this.defaultLabelCounter++;
        this.caseLabelCounter += caseStart;
    }

    /*****************************************
    * END STMT AREA                          *
    *****************************************/

    /*****************************************
    * START TYPE AREA                        *
    *****************************************/
    public void caseAIntType(AIntType node) {
        node.getInt().apply(this);
    }

    public void caseARealType(ARealType node) {
        node.getReal().apply(this);
    }

    public void caseAStringType(AStringType node) {
        node.getString().apply(this);
    }

    public void caseABoolType(ABoolType node) {
        node.getBool().apply(this);
    }

    public void caseAVarIdbool(AVarIdbool node) {
        node.getId().apply(this);
    }

    public void caseABoolIdbool(ABoolIdbool node) {
        node.getBoolean().apply(this);
    }

    public void caseATrueBoolean(ATrueBoolean node) {
        flapjacks.push(1);
    }

    public void caseAFalseBoolean(AFalseBoolean node) {
        flapjacks.push(0);
    }

    public void caseACondBoolean(ACondBoolean node) {
        node.getExpr().apply(this);
        node.getCond().apply(this);
        node.getRight().apply(this);
        Object rightExp = flapjacks.pop();
        String cond = flapjacks.pop().toString();
        Object leftExp = flapjacks.pop();

        String reg1 = this.incrementRegister();
        String reg2 = this.incrementRegister();
        String reg3 = this.incrementRegister();
        //Load left and right into registers
        //Compare them into a new register, push register onto stack
        if(leftExp instanceof Integer) {
            mipsString.append("\tli ").append(reg1).append(", ").append(leftExp).append("\n");
        } else if(leftExp instanceof Double) {

        } else if(leftExp instanceof String) {
            //Might have to handle id vs string here
            mipsString.append("\tmove ").append(reg1).append(", ").append(leftExp).append("\n");
        }

        if(rightExp instanceof Integer) {
            mipsString.append("\tli ").append(reg2).append(", ").append(rightExp).append("\n");
        } else if(rightExp instanceof Double) {

        } else if(rightExp instanceof String) {
            //Might have to handle id vs string here
            mipsString.append("\tmove ").append(reg2).append(", ").append(rightExp).append("\n");
        }

        mipsString.append("\t").append(cond).append(" ").append(reg3).append(", ").append(reg1).append(", ").append(reg2).append("\n");
        flapjacks.push(reg3);
    }

    /*****************************************
    * END TYPE AREA                          *
    *****************************************/

    /*****************************************
    * START EXPR AREA                        *
    *****************************************/

    public void caseATermExpr(ATermExpr node) {
        node.getTerm().apply(this);
    }

    public void caseAAddopExpr(AAddopExpr node) {
        node.getExpr().apply(this);
        node.getAddop().apply(this);
        node.getTerm().apply(this);
        Object rightExpr = flapjacks.pop();
        String addOp = flapjacks.pop().toString();
        Object leftExpr = flapjacks.pop();
        //this technically won't work if the id is like $f or something but let's pretend that's not a thing
        //anyway if one of them is a float, just convert the other to a float and store it to a float register
        if (rightExpr instanceof String && rightExpr.toString().indexOf("$f") != -1) {
            if (leftExpr instanceof String && leftExpr.toString().indexOf("$f") != -1) {
                String nextRegister = this.incrementFloatRegister();
                if (addOp.equals("add")) {
                    addOp = "add.s";
                }
                else {
                    addOp = "sub.s";
                }
                mipsString.append("\t").append(addOp).append(" ").append(nextRegister).append(", ");
                mipsString.append(rightExpr).append(", ");
                mipsString.append(leftExpr).append("\n");
                flapjacks.push(null);
                flapjacks.push(nextRegister);
            } else {
                String nextRegister = this.incrementFloatRegister();
                mipsString.append("\tmtc1 ").append(leftExpr).append(", ").append(nextRegister).append("\n");
                mipsString.append("\tcvt.s.w ").append(nextRegister + ", " + nextRegister);
                if (addOp.equals("add")) {
                    addOp = "add.s";
                }
                else {
                    addOp = "sub.s";
                }
                mipsString.append("\t").append(addOp).append(" ").append(nextRegister).append(", ");
                mipsString.append(nextRegister).append(", ");
                mipsString.append(rightExpr).append("\n");
                flapjacks.push(null);
                flapjacks.push(nextRegister);
            }
        } else if (leftExpr instanceof String && leftExpr.toString().indexOf("$f") != -1) {
            String nextRegister = this.incrementFloatRegister();
            mipsString.append("\tmtc1 ").append(rightExpr).append(", ").append(nextRegister).append("\n");
            mipsString.append("\tcvt.s.w ").append(nextRegister + ", " + nextRegister);
            if (addOp.equals("add")) {
                addOp = "add.s";
            }
            else {
                addOp = "sub.s";
            }
            mipsString.append("\t").append(addOp).append(" ").append(nextRegister).append(", ");
            mipsString.append(nextRegister).append(", ");
            mipsString.append(leftExpr).append("\n");
            flapjacks.push(null);
            flapjacks.push(nextRegister);
        } else {
            //if we get here neither were floats so whatever carry on
            String nextRegister = this.incrementRegister();
            mipsString.append("\tadd " + nextRegister + ", ");
            mipsString.append(nextRegister + ", ");
            mipsString.append(leftExpr.toString() + "\n");

            mipsString.append("\t" + addOp + " " + nextRegister + ", ");
            mipsString.append(nextRegister + ", ");
            mipsString.append(rightExpr.toString() + "\n");
            flapjacks.push(null);
            flapjacks.push(nextRegister);
        }
    }

    public void caseAPlusAddop(APlusAddop node) {
        node.getPlus().apply(this);
    }

    public void caseAMinusAddop(AMinusAddop node) {
        node.getMinus().apply(this);
    }

    public void caseAMultiplyMultop(AMultiplyMultop node) {
        node.getTimes().apply(this);
    }

    public void caseADivideMultop(ADivideMultop node) {
        node.getDivide().apply(this);
    }

    /*****************************************
    * END EXPR AREA                          *
    *****************************************/

    /*****************************************
    * START Term AREA                        *
    *****************************************/

    public void caseAFactorTerm(AFactorTerm node) {
        node.getFactor().apply(this);
    }

    public void caseAMultopTerm(AMultopTerm node) {
        node.getTerm().apply(this);
        node.getMultop().apply(this);
        node.getFactor().apply(this);
        Object rightFactor = flapjacks.pop();
        String multOp = flapjacks.pop().toString();
        Object leftTerm = flapjacks.pop();
        String nextRegister = this.incrementRegister();
        mipsString.append("\tadd " + nextRegister + ", ");
        mipsString.append(nextRegister + ", ");
        mipsString.append(leftTerm.toString() + "\n");

        mipsString.append("\t" + multOp + " ");
        mipsString.append(nextRegister + ", ");
        mipsString.append(rightFactor.toString() + "\n");

        //only move the lower register, because nobody needs more than 32 bits
        mipsString.append("\tmflo "+ nextRegister + "\n");
        flapjacks.push(null);
        flapjacks.push(nextRegister);
    }

    /*****************************************
    * END TERM AREA                          *
    *****************************************/

    /*****************************************
    * START FACTOR AREA                        *
    *****************************************/

    public void caseAIntegerFactor(AIntegerFactor node) {
        node.getIntnum().apply(this);
    }

    public void caseARealFactor(ARealFactor node) {
        node.getRealnum().apply(this);
        //TODO probably has something to do with this
        String floatDataLabel = "float" + this.floatDataLabelCounter;
        String newRegister = this.incrementFloatRegister();
        Double floatNum = (Double)flapjacks.pop();
        Symbol newSymbol = new Symbol(floatDataLabel, floatNum, "REAL");
        addToSymbolTable(floatDataLabel, newSymbol);
        mipsString.append("\tl.s " + newRegister + ", " + floatDataLabel + "\n");
        flapjacks.push(newRegister);
        this.floatDataLabelCounter++;
    }

    public void caseAIdFactor(AIdFactor node) {
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        String newRegister;
        Symbol symbol = findInSymbolTable(this.symbolTable, id);
        if (symbol.getType().equals("REAL")) {
            newRegister = this.incrementFloatRegister();
            mipsString.append("\tl.s " + newRegister + ", " + symbol.getId() + "\n");
        }
        //TODO actually else?
        else {
            newRegister = this.incrementRegister();
            mipsString.append("\tlw " + newRegister + ", " + symbol.getId() + "\n");
        }
        flapjacks.push(newRegister);
    }


    /*****************************************
    * END FACTOR AREA                          *
    *****************************************/

    /*****************************************
    * START CONDITION AREA                   *
    *****************************************/

    public void caseADbleqlCond(ADbleqlCond node) {
        node.getDoubleequals().apply(this);
    }

    public void caseANeqlCond(ANeqlCond node) {
        node.getNotequals().apply(this);
    }

    public void caseAGteqlCond(AGteqlCond node) {
        node.getGtequals().apply(this);
    }

    public void caseALteqlCond(ALteqlCond node) {
        node.getLtequals().apply(this);
    }

    public void caseAGtCond(AGtCond node) {
        node.getGt().apply(this);
    }

    public void caseALtCond(ALtCond node) {
        node.getLt().apply(this);
    }

    /*****************************************
    * END CONDITION AREA                     *
    *****************************************/

    /*****************************************
    * START TOKEN AREA                       *
    *****************************************/
    public void caseTId(TId node) {
        flapjacks.push(node.getText());
    }

    public void caseTIntnum(TIntnum node) {
        flapjacks.push(Integer.parseInt(node.getText()));
    }

    public void caseTRealnum(TRealnum node) {
        flapjacks.push(Double.parseDouble(node.getText()));
    }

    public void caseTStringlit(TStringlit node) {
        flapjacks.push(node.getText());
    }

    public void caseTTrue(TTrue node) {
        flapjacks.push("1");
    }

    public void caseTFalse(TFalse node) {
        flapjacks.push("0");
    }

    public void caseTDoubleequals(TDoubleequals node) {
        flapjacks.push("seq");
    }

    public void caseTNotequals(TNotequals node) {
        flapjacks.push("sne");
    }

    public void caseTGtequals(TGtequals node) {
        flapjacks.push("sge");
    }

    public void caseTLtequals(TLtequals node) {
        flapjacks.push("sle");
    }

    public void caseTGt(TGt node) {
        flapjacks.push("sgt");
    }

    public void caseTLt(TLt node) {
        flapjacks.push("slt");
    }

    public void caseTBool(TBool node) {
        flapjacks.push(node.getText());
    }

    public void caseTString(TString node) {
        flapjacks.push(node.getText());
    }

    public void caseTReal(TReal node) {
        flapjacks.push(node.getText());
    }

    public void caseTInt(TInt node) {
        flapjacks.push(node.getText());
    }

    public void caseTPlus(TPlus node) {
        flapjacks.push("add");
    }

    public void caseTMinus(TMinus node) {
        flapjacks.push("sub");
    }

    public void caseTTimes(TTimes node) {
        flapjacks.push("mult");
    }

    public void caseTDivide(TDivide node) {
        flapjacks.push("div");
    }
}