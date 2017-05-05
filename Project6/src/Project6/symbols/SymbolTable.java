package Project6.symbols;

import java.util.*;

public class SymbolTable implements ITable {
    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private ITable parent;

    public ITable getParent() {
        return this.parent;
    }

    public void setParent(ITable parent) {
        this.parent = parent;
    }
    
    public void add(String id, Symbol sym) {
        this.symbolTable.put(id, sym);
    }

    public void update(String id, Symbol sym) {
        this.symbolTable.replace(id, sym);
    }

    public boolean containsKey(String id) {
        return this.symbolTable.containsKey(id);
    }

    public Symbol getValue(String id) {
        return this.symbolTable.get(id);
    }

    public Set<String> getKeys() {
        return this.symbolTable.keySet();
    }

    public int size() {
        return this.symbolTable.size();
    }
}