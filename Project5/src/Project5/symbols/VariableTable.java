package Project5.symbols;

import java.util.*;

public class VariableTable {
    private HashMap<String, SymbolTable> variableTables = new HashMap<>();

    public void putVariableTables(String name) {
        variableTables.put(name, new SymbolTable());
    }

    public void add(String name, Symbol sym) {
        variableTables.get(name).add(name, sym);
    }

    public SymbolTable getTable(String name) {
        return variableTables.get(name);
    }
}