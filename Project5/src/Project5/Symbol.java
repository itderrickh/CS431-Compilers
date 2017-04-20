package Project5;

import java.util.*;

public class Symbol {
    private String register;
    private String identifier;
    private Object value;

    public Symbol(String identifier, Object value) {
       this.register = "";
       this.identifier = identifier;
       this.value = value;
    }

    public Object getValue() {
        return this.value;
    }
}