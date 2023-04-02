package Logica;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;


public class FileMethods implements FileLogica{

    @Override
    public  void create(String path, Socket socket)  throws Exception{

        try {
            var dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int bytes = 0;

            FileInputStream fileInputStream = null;
            File file = new File(path);
            fileInputStream = new FileInputStream(path);

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


        File file = new File(fileName);
        if(file.exists())
        {
            var toDelete = file.delete();
            System.out.println("bestaande file is verwijderd"+ toDelete);
        }

        var dataInputStream = new DataInputStream(socket.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        int bytes = 0;

        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }

    @Override
    public void deleteFile(String filename)
    {
        File file = new File(filename);

        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }
    }
}
