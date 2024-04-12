import java.io.*;
import java.util.Scanner;
import java.net.*;
import tryy.threadss;

public class tryServer{
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(5454);
        System.out.println("Waiting for Server!... Please wait....");
        Socket s = ss.accept();
        System.out.println("Connection established!!");
        threadss t = new threadss();
        t.s = s;


        //We are starting the threads of both the thread
        t.receiveThread.start();
        t.sendThread.start();
    }
}