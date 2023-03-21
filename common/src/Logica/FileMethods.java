package Logica;
import java.io.*;
import java.net.Socket;



public class FileMethods implements FileLogica{

    @Override
    public  void create(String path, Socket socket)  throws Exception{

        try {
            var dataOutputStream = new DataOutputStream(socket.getOutputStream());

            var name = path.substring(path.lastIndexOf('/') + 1);
            String savePath = "server" + "/" + "src" + "/" + "data" +  "/" + name;
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

    @Override()
    public boolean checkServerForFile(String Filename)
    {
        File folder = new File("server/src/data");
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.getName().equalsIgnoreCase(Filename)) {
                System.out.println(file.getName());
                return true;
            }
        }
        return false;
    };
}
