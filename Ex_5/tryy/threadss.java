package tryy;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class threadss {
    public Socket s = null;
    public DataInputStream din = null;
    public DataOutputStream dos = null;
    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public BufferedInputStream bis = null;


    public Thread receiveThread = new Thread(() ->{
        try{
            DataInputStream din = new DataInputStream(s.getInputStream());
            while(true){
                String str = din.readUTF();
                if(str.equalsIgnoreCase("bye")){
                    System.out.println("Server has exited the chat!!");
                    break;
                }

                try {
                    if(str.equalsIgnoreCase("attach")){

                        String file_name = din.readUTF();
                        long fileSize = din.readLong();
                        String Dir = "C:\\Users\\Nivas\\Documents\\Programming languages\\CN\\LAB_PRGs\\Ex_5\\tryy\\";
                        fos = new FileOutputStream(Dir + file_name);
                        byte[] buffer = new byte[1024 * 1024];
                        int bytesRead = 0;
                        long totalBytesRead = 0;
                        while (totalBytesRead < fileSize && (bytesRead = din.read(buffer, 0, (int)Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
                            fos.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                        }
                        System.out.println("File received successfully!!");




                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Client msg : "+str);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    });

    public Thread sendThread = new Thread(() ->{
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

                try {
                    if(str.equalsIgnoreCase("attach")){
                        System.out.println("Enter the file path to attach : ");
                        String path = sr.nextLine();
                        File file = new File(path);

                        if(!file.exists()){
                            System.out.println("File does not exist!!");
                            System.exit(1);
                        }
                        long file_length = file.length();
                        String file_name = file.getName();
                        dos.writeUTF(file_name);
                        dos.writeLong(file_length);
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis);
                        byte[] buffer = new byte[1024 * 1024];
                        int bytesRead = 0;
                        long totalBytesRead = 0;
                        while ((bytesRead = bis.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                            dos.flush();
                            totalBytesRead += bytesRead;
                        }
                        System.out.println("File sent successfully!!");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    });
}
