import java.io.*;
import java.net.Socket;

public class KSocket extends Socket {
    private String host;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public KSocket(Socket socket) {
        this.socket = socket;
        this.host = "localhost";
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error connecting to " + host);
            System.exit(1);
        }
    }

    public KSocket(String host, int port) {
        this.host = host;
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error connecting to " + host);
            System.exit(1);
        }

    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from " + host);
            System.exit(1);
            return "";
        }
    }

    public void writeLine(String msg) {
        out.println(msg);
    }

}
