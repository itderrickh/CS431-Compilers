package Project5.symbols;

public class GlobalTable {
    private HashMap<String, SymbolTable> globalTable = new HashMap<>();
    private HashMap<String, ClassTable> classTables = new HashMap<>();

    public void putClassTable(String name) {
        ClassTable t = classTables.get(name);
        if(t == null) {
            classTables.put(name, new ClassTable());
        }
    }

    private void add(String name, sym) {
        globalTable.get(name).add(name, sym);
    }

    private void addToClassTable(String classTableString name, Symbol sym) {
        classTables.get(classTableString).add(name, sym);
    }
}