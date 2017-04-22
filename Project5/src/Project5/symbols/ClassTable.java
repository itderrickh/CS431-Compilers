package Project5.symbols;

public class ClassTable {
    private HashMap<String, SymbolTable> classTables = new HashMap<>();
    private HashMap<String, MethodTable> methodTable = new HashMap<>();

    public void putMethodTable(String name) {
        methodTable.put(name, new MethodTable());
    }

    private void add(String name, sym) {
        classTables.get(name).add(name, sym);
    }

    private void addToMethodTable(String name, Symbol sym) {
        methodTable.get(name).add(name, sym);
    }
}