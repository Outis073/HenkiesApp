package Logica;

import java.net.Socket;

public interface FileLogica {

    public void create(String path, Socket socket) throws Exception;
    public void receiveFile(String fileName, Socket socket) throws Exception;
}
