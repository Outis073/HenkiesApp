import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class KConsole {
    BufferedReader in;
    PrintWriter out;

    public KConsole() {
        out = new PrintWriter(System.out, true);
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from console");
            System.exit(1);
            return "";
        }
    }

    public void writeLine(String msg) {
        out.println(msg);
    }

}
