import java.io.*;
import java.net.*;

public class Server_side3 {
    public static void main(String[] args){
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        FileOutputStream fos = null;

        try{
            ss = new ServerSocket(5454);
            s = ss.accept();
            System.out.println("Connection established!!");
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            fos = new FileOutputStream("server_file.txt");
            byte[] buffer = new byte[4096];
            int filesize = 15123;
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0){
                totalRead += read;
                remaining -= read;
                System.out.println("read " + totalRead + " bytes.");
                fos.write(buffer, 0, read);
            }
            System.out.println("File received successfully!!");
            dos.writeUTF("File received successfully!!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if(fos != null) fos.close();
                if(dis != null) dis.close();
                if(dos != null) dos.close();
                if(s != null) s.close();
                if(ss != null) ss.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
