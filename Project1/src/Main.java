public class Main {
    public static void main(String[] args) {
        Tokenizer t = Tokenizer.newTokenizer();
        FileReader fr = FileReader.newFileReader("prog1.txt", t);

        try {
            String result = fr.getTokens();
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println("There was a problem scanning: " + t.getString());
        }
        
    }
}