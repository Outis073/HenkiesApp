import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class KServerSocket  {
    private int port;
    private ServerSocket socket;

    public KServerSocket(int port) {
        this.port = port;
        try {
            socket = new ServerSocket(port);

        } catch (IOException e) {
            System.out.println("Error when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    public KSocket accept() {
        try {
            return new KSocket(socket.accept());

        } catch (IOException e) {
            System.out.println("Error when trying to listen on port " + port);
            System.out.println(e.getMessage());
            System.exit(1);
            return null;
        }
    }
}
