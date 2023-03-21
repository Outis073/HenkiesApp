package Logica;
import java.io.*;
import java.net.Socket;



public class FileMethods implements FileLogica{



    @Override
    public  void create(String path, Socket socket)  throws Exception{

        try {

            var dataOutputStream = new DataOutputStream(socket.getOutputStream());


            int bytes = 0;
            File file = new File(path);
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(file);


            // send file size
            dataOutputStream.writeLong(file.length());
            // break file into chunks
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer,0,bytes);
                dataOutputStream.flush();
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void receiveFile(String fileName, Socket socket) throws Exception{
        var dataInputStream = new DataInputStream(socket.getInputStream());

        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }
}
