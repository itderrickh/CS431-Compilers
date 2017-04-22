package Project5.symbols;

public class GlobalTable {
    private HashMap<String, SymbolTable> globalTable = new HashMap<>();
    private HashMap<String, ClassTable> classTables = new HashMap<>();

    public void putClassTable(String name) {
        classTables.put(name, new ClassTable());
    }

    private void add(String name, sym) {
        globalTable.get(name).add(name, sym);
    }

    private void addToClassTable(String name, Symbol sym) {
        classTables.get(name).add(name, sym);
    }
}