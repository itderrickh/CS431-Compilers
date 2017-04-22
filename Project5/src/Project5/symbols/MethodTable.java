package Project5.symbols;

import java.util.*;

public class MethodTable {
    private HashMap<String, SymbolTable> methodTables = new HashMap<>();
    private HashMap<String, VariableTable> variableTable = new HashMap<>();

    public void putVariableTable(String name) {
        VariableTable t = variableTable.get(name);
        if(t == null) {
            variableTable.put(name, new VariableTable());
        }
    }

    public void add(String name, Symbol sym) {
        methodTables.get(name).add(name, sym);
    }

    public void addToVariableTable(String variableTableName, String name, Symbol sym) {
        variableTable.get(variableTableName).add(name, sym);
    }

    public VariableTable getVariableTable(String name) {
        return variableTable.get(name);
    }
}