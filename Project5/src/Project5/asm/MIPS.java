package Project5.asm;

public class MIPS {
    private StringBuilder body = new StringBuilder();
    private StringBuilder data = new StringBuilder();

    public void addLine(boolean tab, String[] args) {
        if(tab) {
            body.append("\t");
        }
        for(String i : args) {
            body.append(i);
        }
        body.append("\n");
    }
}