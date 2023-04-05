import Logica.FileMethods;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Server {
    public static void main(String[] args) {

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
    private String serverPath = null;

    FileMethods logica = new FileMethods();

    Handler(KConsole console, KSocket socket) {
        this.console = console;
        this.socket = socket;
    }

    public void run() {
        String inputLine, outputLine;
        Protocol protocol = new Protocol();
        outputLine = protocol.processInput(null);
        socket.writeLine(outputLine);
        List<String> filesNotToSync = new ArrayList<>();

        while ((inputLine = socket.readLine()) != null) {
            console.writeLine("Client: " + inputLine);

            if(inputLine.contains("serverDir"))
            {
                serverPath = inputLine.substring(inputLine.lastIndexOf('-') + 1);
            }

            if (!inputLine.contains("0x"))
            {
                if(inputLine.contains("reset"))
                {
                    outputLine = protocol.processInput(null);
                }else
                {
                    outputLine = protocol.processInput(inputLine);
                }
                socket.writeLine(outputLine);
            }

            if(inputLine.contains("0x06"))
            {
                try{
                    String name = inputLine.substring(inputLine.indexOf("-")+1, inputLine.lastIndexOf("-"));
                    String path = serverPath  + "\\"+name;
                    logica.receiveFile(path, socket.getSocket());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(inputLine.contains("0x08"))
            {
                try{
                    String name = inputLine.substring(inputLine.indexOf("-")+1, inputLine.lastIndexOf("-"));
                    console.writeLine(name);
                    String path = serverPath  + "\\"+name;
                    logica.deleteFile(path);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(inputLine.contains("0x02"))
            {
                try{
                    String fileName = inputLine.substring(inputLine.indexOf("-")+1, inputLine.lastIndexOf("-"));
                    var clientModDate = inputLine.substring(inputLine.lastIndexOf('-') + 1);

                    boolean check = new File(serverPath+ "\\"+ fileName).exists();

                    if(check)
                    {
                        var checkDate = new File(serverPath+ "\\"+fileName).lastModified();
                        if (checkDate < Long.parseLong(clientModDate))
                        {
                            filesNotToSync.add(fileName);
                        }
                    }

                    if(fileName.equals("EndOfArrayFromClient"))
                    {
                        List<String> filesFromServer= new ArrayList<>();
                        File folder = new File(serverPath+ "\\"+fileName);
                        File[] listOfFiles = folder.listFiles();

                        for (int i = 0; i < listOfFiles.length; i++) {
                            if (listOfFiles[i].isFile()) {
                                filesFromServer.add(listOfFiles[i].getName());
                            } else if (listOfFiles[i].isDirectory()) {
                                System.err.println("Verkeerde directory gekozen");
                            }
                        }
                        filesFromServer.removeAll(new HashSet(filesNotToSync));

                        for (String element: filesFromServer)
                        {
                            console.writeLine("0x06 -" + element);
                            socket.writeLine("0x06 -" + element);

                            logica.create(serverPath + "\\" + element, socket.getSocket());
                            Thread.sleep(200);
                        }
                        filesFromServer.clear();
                        filesNotToSync.clear();

                        socket.writeLine("Einde synchronisatie");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputLine.equals("Bye.")) {
                break;
            }
        }
    }
}
