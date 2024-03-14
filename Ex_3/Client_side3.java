import java.io.*;
import java.net.*;
public class Client_side3 {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost", 5454);
        System.out.println("Connection established to Server!!");
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream("C:\\Users\\Nivas\\Documents\\Programming languages\\CN\\LAB_PRGs\\Ex_3\\client_file.txt");
        byte[] buffer = new byte[4096];
        int read = 0;
        while((read = fis.read(buffer)) > 0){
            dos.write(buffer, 0, read);
        }
        dos.flush();
        System.out.println("File sent successfully!!");
        System.out.println(dis.readUTF());
        fis.close();
        dos.close();
        dis.close();
        s.close();
    }
}
