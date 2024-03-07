import java.util.*;
import java.io.*;
import java.net.*;

public class Server_side2 {
    public static void main(String[] args)throws Exception{
        ServerSocket ss = new ServerSocket(8080);
        Socket s = ss.accept();
        System.out.println("Connection is stable now");
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "", str2 = "";
        while(!str.equals("stop")){
            str = din.readUTF();
            System.out.println("Client says: "+str);
            str2 = br.readLine();
            dout.writeUTF(str2);
            dout.flush();
        }
        din.close();
        s.close();
        //ss.close();
    }
}
