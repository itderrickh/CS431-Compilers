import java.util.HashMap;
import java.util.ArrayList;

public class Tokenizer {
    private HashMap<String, String> validSymbols = new HashMap<String, String>();
    private ArrayList<Character> items = new ArrayList<Character>();
    private Tokenizer() { /* Sealed constructor */ }
    public static Tokenizer newTokenizer() {
        Tokenizer t = new Tokenizer();

        t.validSymbols.put("class", "<TClass>");
        t.validSymbols.put("return", "<TReturn>");
        t.validSymbols.put("public", "<TPublic>");
        t.validSymbols.put("static", "<TStatic>");
        t.validSymbols.put("length", "<TLength>");
        t.validSymbols.put("void", "<TVoid>");
        t.validSymbols.put("main", "<TMain>");
        t.validSymbols.put("System.out.println", "<TPrint>");
        t.validSymbols.put("String", "<TString>");
        t.validSymbols.put("boolean", "<TBool>");
        t.validSymbols.put("int", "<TInt>");
        t.validSymbols.put("if", "<TIf>");
        t.validSymbols.put("else", "<TElse>");
        t.validSymbols.put("while", "<TWhile>");
        t.validSymbols.put("true", "<TTrue>");
        t.validSymbols.put("false", "<TFalse>");
        t.validSymbols.put("this", "<TThis>");
        t.validSymbols.put("new", "<TNew>");
        t.validSymbols.put("+", "<TPlus>");
        t.validSymbols.put("-", "<TMinus>");
        t.validSymbols.put("*", "<TTimes>");
        t.validSymbols.put("/", "<TDivide>");
        t.validSymbols.put("=", "<TEqual>");
        t.validSymbols.put("!", "<TExc>");
        t.validSymbols.put(";", "<TSemicolon>");
        t.validSymbols.put(".", "<TPeriod>");
        t.validSymbols.put(",", "<TComma>");
        t.validSymbols.put("(", "<TLparen>");
        t.validSymbols.put(")", "<TRparen>");
        t.validSymbols.put("{", "<TLcurly>");
        t.validSymbols.put("}", "<TRcurly>");
        t.validSymbols.put("[", "<TLbracket>");
        t.validSymbols.put("]", "<TRbracket>");
        t.validSymbols.put("<", "<TLt>");
        t.validSymbols.put(">", "<TGt>");
        t.validSymbols.put("&&", "<TAnd>");

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
            return "<TComments>";
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

    public boolean isNumber(Character c) {
        String val = this.getStringRepresentation(this.items);
        val += c;
        return isNumber(val);
    }

    //Because numbers can be "4.", we need to attempt to parse things character by character.
    //so, parse the first value as it has to start with an integer, then keep attempting to parse until you reach something you don't recognize or a second decimal point.
    private boolean isNumber(String str) {
        try {
            boolean sawDecimal = false;
            for (int i = 0; i < str.length(); i++) {
                String curChar = Character.toString(str.charAt(i));
                if (curChar.equals(".") && sawDecimal) {
                    return false;
                }
                else if (curChar.equals(".") && !sawDecimal) {
                    sawDecimal = true;
                }
                else {
                    Integer.parseInt(curChar);
                }
            }
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean isIdentifier() {
        return isIdentifier(getStringRepresentation(this.items));
    }

    public boolean isIdentifier(Character c) {
        String val = this.getStringRepresentation(this.items);
        val += c;
        return isIdentifier(val);
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