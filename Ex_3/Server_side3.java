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
            String filename = dis.readUTF();
            int filesize = dis.readInt();

            dos = new DataOutputStream(s.getOutputStream());
            fos = new FileOutputStream(filename);
            byte[] buffer = new byte[4096];
            int read = 0;
            int totalRead = 0;

            while(totalRead < filesize && (read = dis.read(buffer, 0, Math.min(buffer.length, filesize - totalRead))) != -1){
                totalRead += read;
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
