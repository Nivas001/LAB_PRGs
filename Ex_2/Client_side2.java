import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client_side2{
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost", 5454);
        System.out.println("Connection established to Server!!");

        Thread receiveThread = new Thread(() ->{
            try{
                DataInputStream din = new DataInputStream(s.getInputStream());
                while(true){
                    String str = din.readUTF();
                    if(str.equalsIgnoreCase("bye")){
                        System.out.println("Server has exited the chat!!");
                        break;
                    }
                    System.out.println("Server msg : "+str);
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