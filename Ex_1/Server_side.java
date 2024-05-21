import java.io.*;
import java.net.*;
public class Server_side {
    public static void main(String[] args){
        try{
            //
            ServerSocket sc = new ServerSocket(1022);

            Socket s = sc.accept();

            //Get input from client so pass the socket object
            DataInputStream dis = new DataInputStream(s.getInputStream());

            while(true){
                String str = (String)dis.readUTF();
                System.out.println("The message from client is : "+str);
                if(str.equals("exit")){
                    break;
                }
            }

            sc.close();

        }
        catch(Exception e){
            System.out.println("The error occured is : "+e);
        }
    }
}
