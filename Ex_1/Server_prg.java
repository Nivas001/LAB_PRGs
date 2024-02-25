import java.io.*;
import java.net.*;

public class Server_prg {
    public static void main(String[] args){
        try{
            ServerSocket soc = new ServerSocket(6565);

            Socket s = soc.accept();

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String wrd = (String)dis.readUTF();

            System.out.println("His/Her Name : "+wrd);
            System.out.println(wrd+"!! Welcome to CN World");

            soc.close();
        }
        catch (Exception e){
            System.out.println("Error occurred in - "+e);
        }
    }
}
