import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) {
        int port = 9999;
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            //To open the incoming connections ss
            ss = new ServerSocket(port);
            System.out.println("Socket created with port " + port);
            System.out.println("Waiting for a client to connect...");
            s = ss.accept();
            System.out.println("Client connected: " +s.getInetAddress().getHostAddress());
            dis = new DataInputStream(s.getInputStream());
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();
            fos = new FileOutputStream("_" + fileName);
            byte[] buffer = new byte[1024 * 1024];
            int bytesRead;
            long totalBytesRead = 0;
            while (totalBytesRead < fileSize && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            System.out.println("File transfer complete");
        } catch (IOException e) {
            System.out.println("Error in file transfer: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                if (dos != null)
                    dos.close();
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (s != null)
                    s.close();
                if (ss != null)
                    ss.close();
            } catch (IOException e) {
                System.out.println("Error closing resources: " +
                        e.getMessage());
            }
        }
    }
}

