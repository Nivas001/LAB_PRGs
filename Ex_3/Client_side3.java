import java.io.*;
import java.net.*;
import java.util.*;
public class Client_side3 {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        DataInputStream dis = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;

        try{
            Socket s = new Socket("localhost", 5454);
            System.out.println("Connection established to Server!!");
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            System.out.print("Enter the filename to send : ");
            String filename = sc.nextLine();

            File file = new File(filename);
            if(!file.exists()){
                System.out.println("File not found!!");
                dos.writeUTF("File not found!!");
                dos.flush();
                dos.close();
                dis.close();
                s.close();
                return;
            }
            long filesize = file.length();
            dos.writeUTF(file.getName());
            dos.writeLong(filesize);

            fis = new FileInputStream(filename);
            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;

            while((read = fis.read(buffer)) > 0){
                totalRead += read;
                dos.write(buffer, 0, read);
            }
            dos.flush();
            System.out.println("File sent successfully!!");
            System.out.println(dis.readUTF());
        }
        catch (UnknownHostException e){
            System.out.println("Unable to connect : "+e.getMessage());
            System.exit(1);
        }
        catch (IOException e){
            System.out.println("I/O Error : "+e.getMessage());
            System.exit(1);
        }
        finally{
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            if(dis != null)
                dis.close();
        }

    }
}
