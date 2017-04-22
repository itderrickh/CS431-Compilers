package Project5.symbols;

public class ClassTable {
    private HashMap<String, SymbolTable> classTables = new HashMap<>();
    private HashMap<String, MethodTable> methodTable = new HashMap<>();

    public void putMethodTable(String name) {
        MethodTable t = methodTable.get(name);
        if(t == null) {
            methodTable.put(name, new MethodTable());
        }
    }

    private void add(String name, sym) {
        classTables.get(name).add(name, sym);
    }

    private void addToMethodTable(String methodTableName, String name, Symbol sym) {
        methodTable.get(methodTableName).add(name, sym);
    }

    private 
}