package Project5;

import java.util.*;

public class SymbolTable {
    private HashMap<String, Symbol> SymbolTable;

    public SymbolTable() {
        this.SymbolTable = new HashMap<>();
    }
    
    public void add(String id, Symbol sym) {
        this.SymbolTable.put(id, sym);
    }
}