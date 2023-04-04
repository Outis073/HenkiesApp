import Logica.FileMethods;
import jdk.net.ExtendedSocketOptions;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.File;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private String clientPath = "client/src/data/";


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

        fromUser = null;


        while ((fromServer = socket.readLine()) != null) {

            console.writeLine("Server: " + fromServer);

            if (fromServer.equals("Bye."))
                break;

            //create method
            if(fromServer.equalsIgnoreCase("geef een filepath op voor create"))
            {
                try {

                    var name  = console.readLine();

                    socket.writeLine("0x06 -" + name);
                    System.out.println(
                            "Sending the File  from client to the Server");

                    logica.create(clientPath + name, socket.getSocket());

                    console.writeLine("geef server commando");
                    socket.writeLine("reset");
                    fromUser = null;
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

            }
            if(fromServer.equalsIgnoreCase("geef een filepath op voor het verwijderen"))
            {
                try {
                    var name  = console.readLine();

                    socket.writeLine("0x08 - " + name);

                    console.writeLine("geef server commando");
                    socket.writeLine("reset");
                    fromUser = null;
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

            }
            if(fromServer.equalsIgnoreCase("synchroniseren van bestanden"))
            {
                try {
                    File folder = new File("client/src/data");
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
                            socket.writeLine("0x02-" + listOfFiles[i].getName()+"-"+listOfFiles[i].lastModified());
                        } else if (listOfFiles[i].isDirectory()) {
                            System.err.println("oei oei ongeldige directory");
                        }
                    }

                    socket.writeLine("0x02-EndofArrayFromClient-3827888");

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

            }

            if(fromServer.contains("0x06"))
            {
                try{
                    String name = fromServer.substring(fromServer.lastIndexOf('-') + 1);
                    String path = clientPath+name;

                    console.writeLine(clientPath+name);
                    logica.receiveFile(path, socket.getSocket());

                    console.writeLine("server is synching"+ fromServer);

                    fromUser = "server is syncing";
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(fromServer.contains("einde synchen"))
            {
                console.writeLine("geef server commando");
                socket.writeLine("reset");

                fromUser = null;
            }

            if (fromUser == null) {
                 fromUser = console.readLine();
                 socket.writeLine(fromUser);
            }
        }
    }
}
