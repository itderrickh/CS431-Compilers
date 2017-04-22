package Project5.symbols;

public class VariableTable {
    private HashMap<String, SymbolTable> variableTables = new HashMap<>();

    private void putVariableTables(String name) {
        variableTables.put(name, new SymbolTable());
    }

    private void add(String name, Symbol sym) {
        variableTables.get(name).add(name, sym);
    }

    private SymbolTable getTable(String name) {
        return variableTables.get(name);
    }
}