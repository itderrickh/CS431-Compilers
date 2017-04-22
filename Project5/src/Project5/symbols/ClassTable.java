package Project5.symbols;

import java.util.*;

public class ClassTable {
    private HashMap<String, SymbolTable> classTables = new HashMap<>();
    private HashMap<String, MethodTable> methodTable = new HashMap<>();

    public void putMethodTable(String name) {
        MethodTable t = methodTable.get(name);
        if(t == null) {
            methodTable.put(name, new MethodTable());
        }
    }

    public void add(String name, Symbol sym) {
        classTables.get(name).add(name, sym);
    }

    public void addToMethodTable(String methodTableName, String name, Symbol sym) {
        methodTable.get(methodTableName).add(name, sym);
    }

    public MethodTable getMethodTable(String name) {
        return methodTable.get(name);
    }
}