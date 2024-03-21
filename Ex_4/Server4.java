import java.io.*;
import java.net.*;

public class Server4 {
    public static void main(String args[]){

        DatagramSocket ds = null;

        try {
            ds = new DatagramSocket (5555);
            byte[] receive = new byte[1024];
            System.out.println("Server is running...");
            while (true) {
                DatagramPacket dp = new DatagramPacket(receive, receive.length);
                ds.receive(dp);
                String str = new String(dp.getData(), 0, dp.getLength());
                InetAddress senderAddress = dp.getAddress();
                System.out.println("Client: " + str);
                System.out.println("Client IP: " + senderAddress.getHostAddress() + "Port: " + dp.getPort());

                if (str.equals("exit")) {
                    System.out.println("Server is closing...");
                    break;
                }

            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            if(ds != null) {
                ds.close();
            }
        }
    }
}
