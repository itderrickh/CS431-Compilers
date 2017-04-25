package Project5.symbols;

import java.util.*;

public class GlobalTable implements ITable {
    public SymbolTable globalVariables = new SymbolTable();
    public HashMap<String, ClassTable> globalClasses = new HashMap<>();
    private ITable parent;

    public ITable getParent() {
        return this.parent;
    }

    public void setParent(ITable parent) {
        this.parent = parent;
    }
}