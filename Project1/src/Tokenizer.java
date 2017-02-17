import java.util.HashMap;
import java.util.ArrayList;

public class Tokenizer {
    private HashMap<String, String> validSymbols = new HashMap<String, String>();
    private ArrayList<Character> items = new ArrayList<Character>();
    private Tokenizer() { /* Sealed constructor */ }
    public static Tokenizer newTokenizer() {
        Tokenizer t = new Tokenizer();

        t.validSymbols.put("class", "<TClass>");
        t.validSymbols.put("public", "<TClass>");
        t.validSymbols.put("void", "<TVoid>");
        t.validSymbols.put("String", "<TString>");
        t.validSymbols.put("int", "<TTInt>");
        t.validSymbols.put("if", "<TIf>");
        t.validSymbols.put("else", "<Else>");
        t.validSymbols.put(";", "<TSemicolon>");
        t.validSymbols.put(".", "<TPeriod>");
        t.validSymbols.put("(", "<TLparen>");
        t.validSymbols.put(")", "<TRparen>");
        t.validSymbols.put("{", "<TLcurly>");
        t.validSymbols.put("}", "<TRcurly>");
        t.validSymbols.put("[", "<TLbracket>");
        t.validSymbols.put("]", "<TRbracket>");
        t.validSymbols.put("<", "<TLt>");
        t.validSymbols.put(">", "<TGt>");

        return t;
    }

    public void add(Character c) {
        items.add(c);
    }

    public void removeLast() {
        items.remove(items.size() - 1);
    }

    public String getString() {
        return this.getStringRepresentation(this.items);
    }

    public void clear() {
        items = new ArrayList<Character>();
    }

    public String getToken() {
        String val = this.getStringRepresentation(this.items);
        System.out.println(val);
        if(isSymbol(val)) {
            return this.validSymbols.get(val);
        }
        else if(isIdentifier(val)) {
            return "<TId>";
        }
        else if(isNumber(val)) {
            
            return "<TNumber>";
        }
        else if(isComment(val)) {
            return "<TComment>";
        }
        
        return null;
    }

    public boolean isEmpty() {
        return this.items.size() == 0;
    }

    public boolean isSymbol() {
        return isSymbol(getStringRepresentation(this.items));
    }

    private boolean isSymbol(String val) {
        return this.validSymbols.containsKey(val);
    }

    private String getStringRepresentation(ArrayList<Character> list)
    {    
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }

    private ArrayList<Character> getCharArrayRepresentation(String s) {
        //Convert to char array
        ArrayList<Character> chars = new ArrayList<Character>();
        for (char c : s.toCharArray()) {
            chars.add(c);
        }

        return chars;
    }

    public boolean isComment() {
        return isComment(getStringRepresentation(this.items));
    }

    private boolean isComment(String str) {
        if(str.length() > 1) {
            return str.charAt(0) == '/' && str.charAt(1) == '/';
        }

        return false;
    }

    public boolean isNumber() {
        return isNumber(getStringRepresentation(this.items));
    }

    //Because numbers can be "4.", we need to attempt to parse things character by character.
    //so, parse the first value as it has to start with an integer, then keep attempting to parse until you reach something you don't recognize or a second decimal point.
    private boolean isNumber(String str) {
        try {
            boolean sawDecimal = false;
            for (int i = 0; i < str.length(); i++) {
                String curChar = Character.toString(str.charAt(i));
                if (i == 0) {
                    Integer.parseInt(curChar);
                } else {
                    if (curChar == "." && sawDecimal) {
                        return false;
                    }
                    if (curChar == "." && !sawDecimal) {
                        sawDecimal = true;
                    }
                    else {
                        Integer.parseInt(curChar);
                    }
                    
                }
            
            
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        
    }

    public boolean isIdentifier() {
        return isIdentifier(getStringRepresentation(this.items));
    }

    private boolean isIdentifier(String val) {
        //Convert to char array
        ArrayList<Character> chars = getCharArrayRepresentation(val);

        //Needs items to be an identifier
        if(chars.size() > 0) {
            if(Character.isLetter(chars.get(0))) {
                if(chars.size() > 1) {
                    boolean isValid = true;
                    for (Character c : chars) {
                        if(!Character.isDigit(c) && !Character.isLetter(c) && c != '_') {
                            isValid = false;
                        }
                    }

                    return isValid;
                } 
                //Single letter characters are OK
                else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isValid() {
        String val = this.getStringRepresentation(this.items);
        boolean isSymbol = isSymbol(val);
        boolean isIdentifier = isIdentifier(val);
        boolean isNumber = isNumber(val);   
        boolean isComment = isComment(val);

        return isSymbol || isIdentifier || isNumber || isComment;
    }

    public boolean isValid(Character c) {
        String val = this.getStringRepresentation(this.items);
        val += c;
        boolean isSymbol = isSymbol(val);
        boolean isIdentifier = isIdentifier(val);
        boolean isNumber = isNumber(val);
        boolean isComment = isComment(val);

        return isSymbol || isIdentifier || isNumber || isComment;
    }
}