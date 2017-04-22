package Project5.symbols;

public class MethodTable {
    private HashMap<String, SymbolTable> methodTables = new HashMap<>();
    private HashMap<String, VariableTable> variableTable = new HashMap<>();

    public void putVariableTable(String name) {
        variableTable.put(name, new VariableTable());
    }

    private void add(String name, sym) {
        methodTables.get(name).add(name, sym);
    }

    private void addToVariableTable(String name, Symbol sym) {
        variableTable.get(name).add(name, sym);
    }
}