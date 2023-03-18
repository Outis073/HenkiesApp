package Logica;
import java.io.*;

public class FileMethods implements FileLogica{

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    @Override
    public  void create(String path)  throws Exception{

        try {
            int bytes = 0;
            File file = new File(path);
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(file);


            // send file size
            dataOutputStream.writeLong(file.length());
            // break file into chunks
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytes);
                dataOutputStream.flush();
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void receiveFile(String fileName) throws Exception{
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
