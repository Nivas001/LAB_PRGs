import java.io.*;
import java.net.*;
import java.util.Scanner;
class Rec1 extends Thread{
    Socket s;
    Rec1(Socket s){
        this.s=s;
    }
    public void run(){
        try{
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataInputStream din=new DataInputStream(s.getInputStream());
            Scanner scan= new Scanner(System.in);
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String str="",recFile="";
            while(!str.equals("exit")){
                str=in.readUTF();
                if(str.equalsIgnoreCase("attd"))
                {
                    System.out.println("USER-B Sending file Please Wait...");
                    recFile=din.readUTF();
                    File rec = new File("RF/"+recFile);
                    if(rec.exists()){
                        System.out.println("This file already exists, do you want to replace it(yes/no)?");
                                String ch=scan.next();
                        if(ch.equalsIgnoreCase("yes"))
                        {
                            rec.delete();
                        }
                        else if(ch.equalsIgnoreCase("no")){
                            while(true)
                            {
                                int fin=din.readInt();
                                if(fin==-1){
                                    break; }
                            }
                            continue;
                        }
                    }
                    DataOutputStream writer = new DataOutputStream(new
                            FileOutputStream("RF/"+recFile));
                    while(true)
                    {
                        int fin=din.readInt();
                        if(fin==-1){
                            break;
                        }
                        writer.write(fin);
                    }
                    writer.close();
                    System.out.println("File recived From USER-B: "+recFile);
                    continue;
                }
                System.out.println("\nUser-B: "+str+"\n");
            }
            in.close();
            s.close();
            System.exit(0);
        }
        catch(IOException a){
        }
    }
}
public class Server_5{
    public static void main(String[] args) throws IOException{
        Scanner scan =new Scanner(System.in);
        ServerSocket ss =new ServerSocket(4040);
        System.out.println("Waiting for user-B");
        Socket s=ss.accept();
        Rec1 r = new Rec1(s);
        r.start();
        System.out.println("Connected With User-B");
        DataOutputStream out= new DataOutputStream(s.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        String str="";
        while(!str.equals("bye")){
            str=br.readLine();
            out.writeUTF(str);
            if(str.equalsIgnoreCase("attd")){
                try{
                    System.out.print("Enter the file path: ");
                    String filePath =scan.next();
                    if(filePath.equals("exit") || filePath.equals("EXIT")){
                        continue;
                    }
                    System.out.print("Enter the file name: ");
                    String fileName =scan.next();
                    dout.writeUTF(fileName);
                    String Desti = filePath +"/"+fileName;
                    File file = new File(Desti);
                    DataInputStream reader = new DataInputStream(new
                            FileInputStream(file));
                    int fout=0;
                    while(fout!=-1)
                    { fout=reader.read();
                        dout.writeInt(fout);
                    }
                    dout.flush();
                    System.out.println("File transmitted.");
                    reader.close();
                }
                catch (Exception e) {
                    System.out.println("Enter the valid File path and name.");
                }
            }
            out.flush();
        }
        out.close();
        ss.close();
        s.close();
        scan.close();
    }
}