import Logica.FileMethods;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.util.Scanner;

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

        FileMethods logica = new FileMethods();

        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;

        while ((fromServer = socket.readLine()) != null) {

            console.writeLine("Server: " + fromServer);

            if (fromServer.equals("Bye."))
                break;

                fromUser = console.readLine();


            if(fromServer.equalsIgnoreCase("geef een filepath op"))
            {

                try {
                    fromUser = console.readLine();

                    dataInputStream = new DataInputStream(
                            socket.getInputStream());
                    dataOutputStream = new DataOutputStream(
                            socket.getOutputStream());
                    System.out.println(
                            "Sending the File to the Server");

                    logica.create(fromUser);

                    dataInputStream.close();

                }
                catch(Exception e) {
                    //  Block of code to handle errors
                }


            }
            if (fromUser != null) {
                //console.writeLine("Client: " + fromUser);
                socket.writeLine(fromUser);
            }


        }
    }
}
