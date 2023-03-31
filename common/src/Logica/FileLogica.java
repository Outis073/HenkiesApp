package Logica;

import java.net.Socket;
import java.sql.Time;
import java.sql.Timestamp;

public interface FileLogica {

    void create(String path, Timestamp timestamp, Socket socket) throws Exception;
    void receiveFile(String fileName, Socket socket) throws Exception;
    //void synchronizeFile(String path, String fileName, Timestamp, Socket socket) throws Exception;
    // hoeft denk ik niet, kan namelijk ook in een fucntie  gestopt worden waarin de delete en create aangeroepen worden.
    void updateFile(String path, String fileName, Timestamp timestamp, Socket socket) throws Exception;
    void getFileFromServer(String fileName, Socket socket);
    void deleteFile(String path, String fileName, Socket socket);
    boolean checkServerForFile(String Filename, Timestamp timestamp);
}
