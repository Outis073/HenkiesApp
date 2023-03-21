package Logica;
import java.io.*;
import java.net.Socket;

public class FileMethods implements FileLogica{

    @Override
    public  void create(String path, Socket socket)  throws Exception{

        try {
            int bytes = 0;
            File file = new File(path);
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(file);


            // send file size
            socket.getOutputStream().write((int) file.length());
            // break file into chunks
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                socket.getOutputStream().write(buffer, 0, bytes);
                socket.getOutputStream().flush();
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void receiveFile(String fileName, Socket socket) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = socket.getInputStream().read();   // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = socket.getInputStream().read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }
}
