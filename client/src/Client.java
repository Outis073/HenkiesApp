import Logica.FileMethods;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Timestamp;
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
        Timestamp timestamp = null;
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



                    var name  = console.readLine();

//                    var x  = logica.checkServerForFile(name);
//
//                    if(x)
//                    {
                        var path = "client/src/data/" + name;
                        socket.writeLine("send file from client - " + name);

                        System.out.println(
                                "Sending the File  from client to the Server");

                        logica.create(path, timestamp,socket.getSocket());

//                    } else
//                    {
//
//                        System.out.println(
//                                "bestand bestaat al");
//                        socket.writeLine("reset");
//                        //fromServer;
//                    }

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
