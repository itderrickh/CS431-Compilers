package Project6.symbols;

import java.util.*;

public class VariableTable implements ITable {
    public SymbolTable innerVariables = new SymbolTable();
    private ITable parent;

    public ITable getParent() {
        return this.parent;
    }

    public void setParent(ITable parent) {
        this.parent = parent;
    }
}