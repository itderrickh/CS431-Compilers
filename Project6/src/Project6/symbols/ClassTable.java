package Project6.symbols;

import java.util.*;

public class ClassTable implements ITable {
    public SymbolTable classVariables = new SymbolTable();
    public HashMap<String, MethodTable> classFunctions = new HashMap<>();
    private ITable parent;

    public ITable getParent() {
        return this.parent;
    }

    public void setParent(ITable parent) {
        this.parent = parent;
    }
}