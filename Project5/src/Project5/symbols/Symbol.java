package Project5.symbols;

import java.util.*;

public class Symbol {
    private String register;
    private String identifier;
    private Object value;
    private String type;

    public Symbol(String identifier, Object value, String type) {
       this.register = "";
       this.identifier = identifier;
       this.value = value;
       this.type = type;
    }

    public Object getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }
}