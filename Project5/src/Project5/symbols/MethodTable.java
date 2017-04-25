package Project5.symbols;

import java.util.*;

public class MethodTable implements ITable {
    public SymbolTable methodVariables = new SymbolTable();
    public HashMap<String, VariableTable> innerScopes = new HashMap<>();
    private ITable parent;

    public ITable getParent() {
        return this.parent;
    }

    public void setParent(ITable parent) {
        this.parent = parent;
    }
}