import java.io.*;
import java.net.*;
import java.util.Scanner;
import tryy.threadss;

public class tryClient {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost", 5454);
        System.out.println("Connection established to Server!!");
        threadss t = new threadss();
        t.s = s;
        t.receiveThread.start();
        t.sendThread.start();
    }
}