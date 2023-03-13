

public class Client {

    public static void main(String[] args) {
        var client = new Client();
        client.run();
    }

    private void run() {
        String host = "localhost";
        int port = 4444;
        KSocket socket = new KSocket(host, port);
        KConsole console = new KConsole();

        String fromServer;
        String fromUser;

        while ((fromServer = socket.readLine()) != null) {

            console.writeLine("Server: " + fromServer);

            if (fromServer.equals("Bye."))
                break;

                fromUser = console.readLine();
            if (fromUser != null) {
                //console.writeLine("Client: " + fromUser);
                socket.writeLine(fromUser);
            }
        }
    }
}
