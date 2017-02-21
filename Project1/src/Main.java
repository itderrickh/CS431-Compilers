public class Main {
    public static void main(String[] args) {
        Tokenizer t = Tokenizer.newTokenizer();
        FileReader fr1 = FileReader.newFileReader("prog1.txt", t);
        t = Tokenizer.newTokenizer();
        FileReader fr2 = FileReader.newFileReader("prog2.txt", t);
        t = Tokenizer.newTokenizer();
        FileReader fr3 = FileReader.newFileReader("prog3.txt", t);

        try {
            String result1 = fr1.getTokens();
            FileWriter.writeToFile("prog1.answer", result1);

            String result2 = fr2.getTokens();
            FileWriter.writeToFile("prog2.answer", result2);

            String result3 = fr3.getTokens();
            FileWriter.writeToFile("prog3.answer", result3);
        } catch (Exception ex) {
            System.out.println("There was a problem scanning: " + t.getString());
        }
    }
}