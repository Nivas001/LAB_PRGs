import java.io.*;
import java.net.*;

public class Client_8 {
    public static void main(String[] args) throws Exception{
        DatagramSocket ss = new DatagramSocket();
        byte[] data=new byte[1024];
        String str1= "",str2="";

        while(!str1.equals("close"))
        {
            System.out.print("Enter Your Message:");
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            str2 = consoleReader.readLine();
            byte[] data1 = str2.getBytes();
            DatagramPacket dp1 = new DatagramPacket(data1,data1.length,InetAddress.getLocalHost(),7222);
            ss.send(dp1);

            DatagramPacket dp = new DatagramPacket(data,data.length);
            ss.receive(dp);
            str1 = new String(dp.getData(),0,dp.getLength());
            System.out.println("Server : "+str1);
        }

        System.out.println("Connection gets Closed");
        ss.close();

    }
}