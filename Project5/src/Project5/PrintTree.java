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
    private ArrayList<Symbol> visitedVariables = new ArrayList<>();
    private StringBuilder errors;
    private StringBuilder warnings;
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
        warnings = new StringBuilder();
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
                        .append(sym.getName())
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
                        .append(sym.getName())
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
                        .append(sym.getName())
                        .append(" is type ")
                        .append(s.getType())
                        .append(" but was assigned type ")
                        .append(sym.getType())
                        .append("\n");
                }
                table.methodVariables.update(id, s);
            } else {
                table.methodVariables.add(id, sym);
            }
        } else if(symbolTable instanceof VariableTable) {
            VariableTable table = (VariableTable) symbolTable;
            if(table.innerVariables.containsKey(id)) {
                Symbol s = table.innerVariables.getValue(id);
                if(!s.getType().equals(sym.getType())) {
                    this.errors.append("Invalid type exception: ")
                        .append(s.getName())
                        .append(" is type ")
                        .append(s.getType())
                        .append(" but was assigned type ")
                        .append(sym.getType())
                        .append("\n");
                }
                table.innerVariables.update(id, s);
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
        if(!visitedVariables.contains(s)) {
            if(!s.isUsed()) {
                this.warnings.append("Variable ")
                    .append(s.getName())
                    .append(" was not used\n");
            }

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
                errors.append("Undeclared variable: " + s.getName() + '\n');
            }

            visitedVariables.add(s);
        }
    }

    public void traverseSymbols() {
        //Go to the top of the symbolTable
        while(!(this.symbolTable instanceof GlobalTable)) {
            this.symbolTable = (ITable)this.symbolTable.getParent();
        }

        GlobalTable gt = (GlobalTable)this.symbolTable;
        if(gt != null) {
            for(String g : gt.globalVariables.getKeys()) {
                Symbol s = gt.globalVariables.getValue(g);
                doSymbol(s);
            }

            for(String gc: gt.globalClasses.keySet()) {
                ClassTable ct = gt.globalClasses.get(gc);
                if(ct != null) {
                    for(String c : ct.classVariables.getKeys()) {
                        Symbol sy = ct.classVariables.getValue(c);
                        doSymbol(sy);
                    }

                    for(String cg: ct.classFunctions.keySet()) {
                        MethodTable mt = ct.classFunctions.get(cg);
                        if(mt != null) {
                            for(String g : mt.methodVariables.getKeys()) {
                                Symbol sym = mt.methodVariables.getValue(g);
                                doSymbol(sym);
                            }

                            for(String mg: mt.innerScopes.keySet()) {
                                VariableTable vt = mt.innerScopes.get(cg);
                                if(vt != null) {
                                    for(String v : vt.innerVariables.getKeys()) {
                                        Symbol symb = vt.innerVariables.getValue(v);
                                        doSymbol(symb);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getErrors() {
        return errors.toString();
    }

    public String getWarnings() {
        return warnings.toString();
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
        data.append(".data\n");

        node.getClassmethodstmts().apply(this);

        //Write the end of program command
        mipsString.append("\tli $v0, 10\n").append("\tsyscall\n");

        //Write the data section
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

        Symbol s = findInSymbolTable(this.symbolTable, id);
        if(s == null) {
            this.errors.append("Undeclared variable: " + id + "\n");
        } else {
            addToSymbolTable(id, s);
            if(value instanceof Integer) {
                String nextReg = this.incrementRegister();
                mipsString.append("\tli ").append(nextReg).append(", ").append(value).append("\n");
                mipsString.append("\tsw ").append(nextReg).append(", ").append(s.getId()).append("\n");
            } else if(value instanceof Double) {
                String nextReg = this.incrementFloatRegister();
                String floatDataLabel = "float" + this.floatDataLabelCounter;
                data.append("\t").append(floatDataLabel).append(": .float ").append(value).append("\n");
                mipsString.append("\tl.s " + nextReg + ", " + floatDataLabel + "\n");
                this.floatDataLabelCounter++;
            }
        }
        
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
        s.setUsed();
        addToSymbolTable(id, s);
    }

    public void caseAVariabledefStmt(AVariabledefStmt node) {
        node.getId().apply(this);
        node.getType().apply(this);
        String type = flapjacks.pop().toString();
        String id = flapjacks.pop().toString();

        addToSymbolTable(id, new Symbol("variable" + this.variableCounter, null, type, id));
        this.variableCounter++;
    }

    public void caseAIfStmt(AIfStmt node) {
        String ifStmt = "if" + this.ifLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        this.ifLabelCounter++;
        this.mainBodyCounter++;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        
        //Check if it is a register or id
        if(value.contains("$")) {
            mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(ifStmt).append("\n");
            mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(bodyPart).append("\n");
        } else {
            String reg = this.incrementRegister();
            Symbol symbol = findInSymbolTable(this.symbolTable, value);
            mipsString.append("\tlw ").append(reg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("1, ").append(ifStmt).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("0, ").append(bodyPart).append("\n");
        }

        mipsString.append(ifStmt).append(":\n");
        changeScope(true, ifStmt);
        node.getStmtseq().apply(this);
        changeScope(false, "");
        mipsString.append("\tj ").append(bodyPart).append("\n");
        mipsString.append(bodyPart).append(": \n");
    }

    public void caseAIfelseStmt(AIfelseStmt node) {
        String ifStmt = "if" + this.ifLabelCounter;
        String elseStmt = "else" + this.ifLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        this.ifLabelCounter++;
        this.elseLabelCounter++;
        this.mainBodyCounter++;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        //Check if it is a register or id
        if(value.contains("$")) {
            mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(ifStmt).append("\n");
            mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(elseStmt).append("\n");
        } else {
            String reg = this.incrementRegister();
            Symbol symbol = findInSymbolTable(this.symbolTable, value);
            mipsString.append("\tlw ").append(reg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("1, ").append(ifStmt).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("0, ").append(elseStmt).append("\n");
        }

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
    }

    public void caseAWhileStmt(AWhileStmt node) {
        String whileStmt = "while" + this.whileLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        this.whileLabelCounter++;
        this.mainBodyCounter++;
        //get the condition
        node.getIdbool().apply(this);

        //Pop something off the stack, an id or a register
        String value = flapjacks.pop().toString();
        //Check if it is a register or id
        if(value.contains("$")) {
            mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(whileStmt).append("\n");
            mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(bodyPart).append("\n");
        } else {
            String reg = this.incrementRegister();
            Symbol symbol = findInSymbolTable(this.symbolTable, value);
            mipsString.append("\tlw ").append(reg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("1, ").append(whileStmt).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("0, ").append(bodyPart).append("\n");
        }

        mipsString.append(whileStmt).append(":\n");
        
        changeScope(true, whileStmt);
        node.getStmtseq().apply(this);
        changeScope(false, "");

        //Repeating this might fix our issue of not having the correct register
        node.getIdbool().apply(this);
        value = flapjacks.pop().toString();

        if(value.contains("$")) {
            mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(whileStmt).append("\n");
        } else {
            String reg = this.incrementRegister();
            Symbol symbol = findInSymbolTable(this.symbolTable, value);
            mipsString.append("\tlw ").append(reg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("1, ").append(whileStmt).append("\n");
        }

        mipsString.append("\tj ").append(bodyPart).append("\n");
        
        mipsString.append(bodyPart).append(": \n");
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
        node.getId().apply(this);
        node.getExpr().apply(this);
        String updateRegister = "";
        if (flapjacks.size() >= 3) {
            updateRegister = flapjacks.pop().toString();
        }

        Object value = flapjacks.pop();
        String id = flapjacks.pop().toString();

        Symbol s = findInSymbolTable(this.symbolTable, id);
        if(s == null) {
            this.errors.append("Undeclared variable: " + id + "\n");
        } else {
            String nextReg = this.incrementRegister();
            mipsString.append("\tli ").append(nextReg).append(", ").append(value).append("\n");
            mipsString.append("\tsw ").append(nextReg).append(", ").append(s.getId()).append("\n");
            addToSymbolTable(id, s);
        }
        
        if (!updateRegister.equals("")) {
            if (updateRegister.indexOf("$f") != -1) {
                mipsString.append("\ts.s ").append(updateRegister).append(", ").append(s.getId()).append("\n");
            }
            else {
                mipsString.append("\tsw ").append(updateRegister).append(", ").append(s.getId()).append("\n");
            }
        }
    }

    public void caseAForStmt(AForStmt node) {
        String forStmt = "for" + this.forLabelCounter;
        String bodyPart = "main" + this.mainBodyCounter;
        this.mainBodyCounter++;

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
        } else if(value instanceof String) {
            type = "REAL";
        }

        Symbol s = null;
        if(sym == null) {
            s = new Symbol("variable" + variableCounter, value, type, id);
            addToSymbolTable(id, s);
            variableCounter++;
        } else {
            s = new Symbol(sym.getId(), value, type, id);
            s.setUsed();
            addToSymbolTable(id, s);
        }

        if (!updateRegister.equals("")) {
            mipsString.append("\tsw ").append(updateRegister).append(", ").append(s.getId()).append("\n");
        }

        node.getBoolean().apply(this);

        //Pop something off the stack, an id or a register
        value = flapjacks.pop().toString();

        //Check if it is a register or id
        if(value.toString().contains("$")) {
            mipsString.append("\tbeq ").append(value).append(", ").append("1, ").append(forStmt).append("\n");
            mipsString.append("\tbeq ").append(value).append(", ").append("0, ").append(bodyPart).append("\n");
        } else {
            String reg = this.incrementRegister();
            Symbol symbol = findInSymbolTable(this.symbolTable, value.toString());
            mipsString.append("\tlw ").append(reg).append(", ").append(symbol.getId()).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("1, ").append(forStmt).append("\n");
            mipsString.append("\tbeq ").append(reg).append(", ").append("0, ").append(bodyPart).append("\n");
        }

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

        if(sym.getType().equals("INT") && sym.getType().equals("BOOLEAN")) {
            mipsString.append("\taddi $v0, $zero, 5\n\tsyscall\n");
            mipsString.append("\tadd ").append(currReg).append(", $zero, $v0\n");
            mipsString.append("\tsw ").append(currReg).append(", ").append(sym.getId()).append("\n");
        } else if(sym.getType().equals("REAL")) {
            mipsString.append("\taddi $v0, $zero, 6\n\tsyscall\n");
            mipsString.append("\ts.s $f0, ").append(sym.getId()).append("\n");
        } else if(sym.getType().equals("STRING")) {
            //We aren't going to handle strings presently
        }
    }

    public void caseAPutcommandStmt(APutcommandStmt node) {
        String currReg;

        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        Symbol sym = findInSymbolTable(this.symbolTable, id);
        sym.setUsed();
        addToSymbolTable(id, sym);

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
        symbol.setUsed();
        addToSymbolTable(id, symbol);
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
        symbol.setUsed();
        addToSymbolTable(id, symbol);
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
        sym.setUsed();
        if(sym == null) {
            this.errors.append("Undeclared variable: " + id + "\n");
        } else {
            addToSymbolTable(id, sym);
            String nextReg = this.incrementRegister();
            mipsString.append("\tli ").append(nextReg).append(", ").append(value).append("\n");
            mipsString.append("\tsw ").append(nextReg).append(", ").append(sym.getId()).append("\n");
        }
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
        this.mainBodyCounter++;
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
        System.out.println(rightExp);

        //if neither is double then do normal stuff
        if (!((rightExp instanceof String && rightExp.toString().indexOf("$f") != -1) || leftExp instanceof String && leftExp.toString().indexOf("$f")!= -1)) {
            String reg1 = this.incrementRegister();
            String reg2 = this.incrementRegister();
            String reg3 = this.incrementRegister();
            //Load left and right into registers
            //Compare them into a new register, push register onto stack
            if(leftExp instanceof Integer) {
                mipsString.append("\tli ").append(reg1).append(", ").append(leftExp).append("\n");
            } else if(leftExp instanceof String) {
                //Might have to handle id vs string here
                mipsString.append("\tmove ").append(reg1).append(", ").append(leftExp).append("\n");
            }

            if(rightExp instanceof Integer) {
                mipsString.append("\tli ").append(reg2).append(", ").append(rightExp).append("\n");
            }  else if(rightExp instanceof String) {
                //Might have to handle id vs string here
                mipsString.append("\tmove ").append(reg2).append(", ").append(rightExp).append("\n");
            }
            mipsString.append("\t").append(cond).append(" ").append(reg3).append(", ").append(reg1).append(", ").append(reg2).append("\n");
            flapjacks.push(reg3);
        } else {    
            //otherwise we need to convert one or the other to double and compare them that way
            //first we need to load them both into the data section, so add them to the labels as floats even if one is an integer
            String label1 = "float" + this.floatDataLabelCounter;   
            this.floatDataLabelCounter++;
            String label2 = "float" + this.floatDataLabelCounter;
            this.floatDataLabelCounter++;
            String reg1 = this.incrementFloatRegister();
            String reg2 = this.incrementFloatRegister();
            String reg3 = this.incrementFloatRegister();    //where the result will be
            //store them in memory
            data.append("\t").append(label1).append(": .float ").append(rightExp).append("\n");
            data.append("\t").append(label2).append(": .float ").append(leftExp).append("\n");
            mipsString.append("\tl.s " + reg1 + ", " + label1 + "\n");  //right exp in reg1
            mipsString.append("\tl.s " + reg2 + ", " + label2 + "\n");  //left exp in reg2
            if (leftExp instanceof String && !(rightExp instanceof String)) {
                //we have to convert right exp (reg1)
                mipsString.append("\tcvt.s.w ").append(reg1).append(", ").append(reg1);
            } else if (rightExp instanceof String && !(leftExp instanceof String)) {
                //we have to convert left exp (reg2)
                mipsString.append("\tcvt.s.w ").append(reg2).append(", ").append(reg2);
            } 
            //then change the comparison
            if (cond.equals("seq")) {
                cond = "c.eq.s";
            }
            if (cond.equals("sne")) {
                cond = "c.ne.s";
            }
            if (cond.equals("sge")) {
                cond = "c.ge.s";
            }
            if (cond.equals("sle")) {
                cond = "c.le.s";
            }
            if (cond.equals("sgt")) {
                cond = "c.gt.s";
            }
            if (cond.equals("slt")) {
                cond = "c.lt.s";
            }
            mipsString.append("\t").append(cond).append(" ").append(reg3).append(", ").append(reg1).append(", ").append(reg2).append("\n");
            flapjacks.push(reg3);
        }
        
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

        String leftReg = "";
        String rightReg = "";
        //Here we will check if leftExpr is an INT, REAL or a register
        if(leftExpr instanceof Integer && !(rightExpr instanceof Integer)) {
            //Do the conversion here if right isn't int
            leftReg = this.incrementFloatRegister();
            String nextIntReg = this.incrementRegister();
            mipsString.append("\tli ").append(nextIntReg).append(", ").append(leftExpr).append("\n");
            mipsString.append("\tmtc1 ").append(nextIntReg).append(", ").append(leftReg).append("\n");
            mipsString.append("\tcvt.s.w ").append(leftReg + ", " + leftReg + "\n");
        } else if(leftExpr instanceof Double) {
            leftReg = this.incrementFloatRegister();
            String floatDataLabel = "float" + this.floatDataLabelCounter;
            data.append("\t").append(floatDataLabel).append(": .float ").append(leftExpr).append("\n");
            mipsString.append("\tl.s " + leftReg + ", " + floatDataLabel + "\n");
            this.floatDataLabelCounter++;
        } else if(leftExpr instanceof String && leftExpr.toString().contains("$")) { //Is register
            leftReg = leftExpr.toString();
        } else if(leftExpr instanceof String) {
            Symbol s = findInSymbolTable(this.symbolTable, leftExpr.toString());
            //Check type of symbol INT vs FLOAT
            if(s.getType().equals("INT")) {
                String nextReg = this.incrementRegister();
                mipsString.append("\tlw ").append(nextReg).append(", ").append(s.getId()).append("\n");
                leftReg = this.incrementFloatRegister();
                mipsString.append("\tcvt.s.w").append(leftReg).append(", ").append(nextReg).append("\n");
                //Convert word to s
            } else if(s.getType().equals("REAL")) {
                leftReg = this.incrementFloatRegister();
                mipsString.append("\tl.s").append(leftReg).append(", ").append(s.getId()).append("\n");
            }
        }
        //Here we will check if rightExpr is an INT, REAL or a register
        if(rightExpr instanceof Integer && !(leftExpr instanceof Integer)) {
            //Do the conversion here if left isn't int
            rightReg = this.incrementFloatRegister();
            String nextIntReg = this.incrementRegister();
            mipsString.append("\tli ").append(nextIntReg).append(", ").append(rightExpr).append("\n");
            mipsString.append("\tmtc1 ").append(nextIntReg).append(", ").append(rightReg).append("\n");
            mipsString.append("\tcvt.s.w ").append(rightReg + ", " + rightReg + "\n");
        } else if(rightExpr instanceof Double) {
            rightReg = this.incrementFloatRegister();
            String floatDataLabel = "float" + this.floatDataLabelCounter;
            data.append("\t").append(floatDataLabel).append(": .float ").append(rightExpr).append("\n");
            mipsString.append("\tl.s " + rightReg + ", " + floatDataLabel + "\n");
            this.floatDataLabelCounter++;
        } else if(rightExpr instanceof String && rightExpr.toString().contains("$")) { //Is register
            rightReg = rightExpr.toString();
        } else if(rightExpr instanceof String) {
            Symbol s = findInSymbolTable(this.symbolTable, rightExpr.toString());
            //Check type of symbol INT vs FLOAT
            if(s.getType().equals("INT")) {
                String nextReg = this.incrementRegister();
                mipsString.append("\tlw ").append(nextReg).append(", ").append(s.getId()).append("\n");
                rightReg = this.incrementFloatRegister();
                mipsString.append("\tcvt.s.w").append(rightReg).append(", ").append(nextReg).append("\n");
                //Convert word to s
            } else if(s.getType().equals("REAL")) {
                rightReg = this.incrementFloatRegister();
                mipsString.append("\tl.s").append(rightReg).append(", ").append(s.getId()).append("\n");
            }
        }

        //Do integer math
        if(rightExpr instanceof Integer && leftExpr instanceof Integer) {
            //if we get here neither were floats so whatever carry on
            String nextRegister = this.incrementRegister();
            mipsString.append("\tadd " + nextRegister + ", ").append(nextRegister + ", ").append(leftExpr.toString() + "\n");
            mipsString.append("\t" + addOp + " " + nextRegister + ", ").append(nextRegister + ", ").append(rightExpr.toString() + "\n");
            flapjacks.push(null);
            flapjacks.push(nextRegister);
        } else {
            //Do float math with leftReg and rightReg
            String nextRegister = this.incrementFloatRegister();
            if (addOp.equals("add")) {
                addOp = "add.s";
            }
            else {
                addOp = "sub.s";
            }
            mipsString.append("\t").append(addOp).append(" ").append(nextRegister).append(", ").append(leftReg).append(", ").append(rightReg).append("\n");
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
    }

    public void caseAIdFactor(AIdFactor node) {
        node.getId().apply(this);
        String id = flapjacks.pop().toString();
        String newRegister;
        Symbol symbol = findInSymbolTable(this.symbolTable, id);
        symbol.setUsed();
        addToSymbolTable(id, symbol);

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