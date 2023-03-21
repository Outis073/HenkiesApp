package Logica;

import java.net.Socket;

public interface FileLogica {

    void create(String path, Socket socket) throws Exception;
    void receiveFile(String fileName, Socket socket) throws Exception;

    boolean checkServerForFile(String Filename);
}
