import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client4 {
    public static void main(String args[]) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            String str = "Hello Server";
            InetAddress ip = InetAddress.getByName("localhost");
            Scanner s = new Scanner(System.in);

            while (true) {
                System.out.print("Enter message: ");
                str = s.nextLine();
                DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 5555);
                ds.send(dp);
                if (str.equals("exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
