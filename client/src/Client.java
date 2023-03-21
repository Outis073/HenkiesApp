import Logica.FileMethods;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;


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

        FileMethods logica = new FileMethods();


        while ((fromServer = socket.readLine()) != null) {

            console.writeLine("Server: " + fromServer);

            if (fromServer.equals("Bye."))
                break;

            if(fromServer.equalsIgnoreCase("geef een filepath op"))
            {

                try {

                    // client/src/
                    var test = "client/src/test.pdf";
                    socket.writeLine("send file from client");

                    //dataInputStream = new DataInputStream(
                    //        socket.getInputStream());
                    //dataOutputStream = new DataOutputStream(
                    //        socket.getOutputStream());
                    System.out.println(
                            "Sending the File to the Server");

                    // logica.create(test, socket.getSocket());

                    // socket.getSocket().getInputStream().close();

                }
                catch(Exception e) {
                    e.printStackTrace();
                }


            }
            fromUser = console.readLine();
            if (fromUser != null) {
                //console.writeLine("Client: " + fromUser);
                socket.writeLine(fromUser);
            }


        }
    }
}
