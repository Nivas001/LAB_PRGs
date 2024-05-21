import java.io.*;
import java.net.*;
import java.util.*;
public class Client_side {
   public static void main(String[] args){
      try{
         Socket s = new Socket("localhost", 1022);
         DataOutputStream dos = new DataOutputStream(s.getOutputStream());

         Scanner sc = new Scanner(System.in);

         while (true){
            System.out.print("Enter your msg : ");
            String name = sc.next();

            if(name.equals("exit")){
               break;
            }
            dos.writeUTF(name);
         }

         dos.flush();
         dos.close();
         s.close();

      }
      catch(Exception e){
         System.out.println("Error occurred in : "+e);
      }


   }
}
