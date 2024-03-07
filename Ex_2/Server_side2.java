import java.io.*;
import java.net.*;
import java.util.*;

public class Server_side2{
    public static void main(String[] args)throws Exception{
        ServerSocket ss = new ServerSocket(5454);
        Socket s = ss.accept();
        System.out.println("Connection established!!");

        Thread receiveThread = new Thread(() ->{
            try{
                DataInputStream din = new DataInputStream(s.getInputStream());
                while(true){
                    String str = din.readUTF();
                    if(str.equalsIgnoreCase("bye")){
                        System.out.println("Client has exited the chat!!");
                        break;
                    }
                    System.out.println("Client msg : "+str);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });
        
        Thread sendThread = new Thread(() ->{
           try {
               DataOutputStream dos = new DataOutputStream(s.getOutputStream());
               Scanner sr = new Scanner(System.in);
               while(true){
                   String str = sr.nextLine();
                   dos.writeUTF(str);
                   dos.flush();
                     if(str.equalsIgnoreCase("bye")){
                          System.out.println("You have exited the chat!!");
                          break;
                     }
               }
           }
           catch (Exception e){
               e.printStackTrace();
           }
        });


        receiveThread.start();
        sendThread.start();



    }
}