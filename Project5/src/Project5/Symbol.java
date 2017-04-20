package Project5;

import java.util.*;

public class Symbol {
    public String register;
    public String identifier;
    public Object value;

    public Symbol(String identifier, Object value) {
       this.register = "";
       this.identifier = identifier;
       this.value = value;
    }
}