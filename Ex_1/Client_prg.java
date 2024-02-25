import java.io.*;
import java.net.*;
import java.util.*;

public class Client_prg {
    public static void main(String[] args){

        try{
            Scanner sc = new Scanner(System.in);

            Socket s = new Socket("localhost", 6565);

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.print("What's your name buddy? ");
            String name = sc.next();

            dos.writeUTF(name);

            dos.flush();
            dos.close();
            s.close();

        }
        catch(Exception e){
            System.out.print("Error occurred at - "+e);
        }
    }
}
