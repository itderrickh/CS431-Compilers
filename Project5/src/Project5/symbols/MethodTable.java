package Project5.symbols;

public class MethodTable {
    private HashMap<String, SymbolTable> methodTables = new HashMap<>();
    private HashMap<String, VariableTable> variableTable = new HashMap<>();

    public void putVariableTable(String name) {
        VariableTable t = variableTable.get(name);
        if(t == null) {
            variableTable.put(name, new VariableTable());
        }
    }

    private void add(String name, sym) {
        methodTables.get(name).add(name, sym);
    }

    private void addToVariableTable(String variableTableName, String name, Symbol sym) {
        variableTable.get(variableTableName).add(name, sym);
    }

    public VariableTable getVariableTable(String name) {
        return variableTable.get(name);
    }
}