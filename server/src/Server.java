

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {

        int port = 4444;
        KServerSocket serverSocket = new KServerSocket(port);
        KConsole console = new KConsole();
        while (true) {
            KSocket socket = serverSocket.accept();
            var handler = new Handler(console, socket);
            handler.start();
        }
    }
}

class Handler extends Thread {

    private KConsole console;
    private KSocket socket;

    Handler(KConsole console, KSocket socket) {
        this.console = console;
        this.socket = socket;
    }

    public void run() {
        String inputLine, outputLine;
        Protocol protocol = new Protocol();

        outputLine = protocol.processInput(null);
        socket.writeLine(outputLine);
        while ((inputLine = socket.readLine()) != null) {
            console.writeLine("Client: " + inputLine);
            outputLine = protocol.processInput(inputLine);
            socket.writeLine(outputLine);
            if (outputLine.equals("Bye.")) {
                break;
            }
        }
    }
}
