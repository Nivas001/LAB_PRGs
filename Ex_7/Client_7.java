import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client_7 {
    public static void main(String[] args) throws Exception {
        int yesno;
        int port = 33333;
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket("localhost", port);
        System.out.println("Connected to server.");
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "", str2 = "";
        while (!str.equals("bye")) {
            System.out.println("\n1.Reply with message \n2.Reply with Document");
            yesno = sc.nextInt();
            switch (yesno) {
                case 1:
                    System.out.println("Enter your message : ");
                    str = br.readLine();
                    dout.writeUTF(str);
                    dout.flush();
                    str2 = din.readUTF();
                    System.out.println("Server says: " + str2);
                    break;
                case 2:
                    String host = "localhost";
                    String fileName = null;
                    Socket clientSocket = null;
                    DataOutputStream dos = null;
                    FileInputStream fis = null;
                    BufferedInputStream bis = null;
                    try {
                        clientSocket = new Socket(host, port);
                        System.out.println("Connected successfully with " + host + " and port " +
                                port);
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter File path: ");
                        fileName = scanner.nextLine();
                        File file = new File(fileName);
                        if (!file.exists()) {
                            System.out.println("File not found: " + fileName);
                            System.exit(1);
                        }
                        long fileSize = file.length();
                        dos = new DataOutputStream(clientSocket.getOutputStream());
                        dos.writeUTF(file.getName());
                        dos.writeLong(fileSize);
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis);
                        byte[] buffer = new byte[1024 * 1024];
                        int bytesRead;
                        long totalBytesRead = 0;
                        while ((bytesRead = bis.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                        }
                        System.out.println("File transfer successfull !");
                    } catch (UnknownHostException e) {
                        System.out.println("Unable to connect: " + e.getMessage());
                        System.exit(1);
                    } catch (IOException e) {
                        System.out.println("Error in file transfer: " + e.getMessage());
                        System.exit(1);
                    } finally {
                        try {
                            if (dos != null)
                                dos.close();
                            if (bis != null)
                                bis.close();
                            if (clientSocket != null)
                                clientSocket.close();
                        } catch (IOException e) {
                            System.out.println("Error closing resources: " + e.getMessage());
                        }
                    }
                    break;
                default:
                    dout.close();
                    s.close();
            }
        }
    }
}
