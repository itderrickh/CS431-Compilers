package Project5.symbols;

import java.util.*;

public class GlobalTable {
    private HashMap<String, SymbolTable> globalTable = new HashMap<>();
    private HashMap<String, ClassTable> classTables = new HashMap<>();

    public void putClassTable(String name) {
        ClassTable t = classTables.get(name);
        if(t == null) {
            classTables.put(name, new ClassTable());
        }
    }

    public void add(String name, Symbol sym) {
        globalTable.get(name).add(name, sym);
    }

    public void addToClassTable(String classTable, String name, Symbol sym) {
        classTables.get(classTable).add(name, sym);
    }

    public ClassTable getClassTable(String name) {
        return classTables.get(name);
    }
}