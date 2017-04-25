package Project5.symbols;

import java.util.*;

public class GlobalTable {
    public SymbolTable globalVariables = new SymbolTable();
    public HashMap<String, ClassTable> globalClasses = new HashMap<>();
}