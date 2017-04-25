package Project5.symbols;

import java.util.*;

public class Symbol {
    private String register;
    private String identifier;
    private Object value;
    private String type;
    private boolean used;
    private String name;

    public Symbol(String identifier, Object value, String type, String name) {
       this.register = "";
       this.identifier = identifier;
       this.value = value;
       this.type = type;
       this.used = false;
       this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.identifier;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed() {
        this.used = true;
    }
}