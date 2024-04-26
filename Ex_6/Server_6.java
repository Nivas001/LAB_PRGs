import java.io.*;
import java.net.*;
import java.util.ArrayList;

import java.util.List;
public class Server_6 {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    public Server_6(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() {
        System.out.println("Server started on port " +
                serverSocket.getLocalPort() + "...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void broadcastMessage(String message, ClientHandler sender) throws
            IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
    private class ClientHandler extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private String username;
        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                username = new String(buffer, 0, bytesRead).trim();
                System.out.println(username + " has joined the chat.");
                broadcastMessage(username + " has joined the chat.", this);
                while (true) {
                    bytesRead = inputStream.read(buffer);
                    String message = new String(buffer, 0, bytesRead).trim();
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    broadcastMessage(username + ": " + message, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    clients.remove(this);
                    System.out.println(username + " has left the chat.");
                    broadcastMessage(username + " has left the chat.", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void sendMessage(String message) throws IOException {
            outputStream.write(message.getBytes());
        }
    }
    public static void main(String[] args) {
        int port = 22554;
        Server_6 server = new Server_6(port);
        server.start();
    }
}
