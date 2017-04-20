package Project5;

import java.util.*;

public class SymbolTable {
    private HashMap<String, Symbol> symbolTable;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
    }
    
    public void add(String id, Symbol sym) {
        this.symbolTable.put(id, sym);
    }

    public void update(String id, Symbol sym) {
        this.symbolTable.replace(id, sym);
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