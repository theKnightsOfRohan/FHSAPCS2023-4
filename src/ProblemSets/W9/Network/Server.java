package ProblemSets.W9.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server is running...");
            ArrayList<String> messages = new ArrayList<>();
            ArrayList<Socket> sockets = new ArrayList<>();

            while (true) {
                System.out.println("Waiting for new connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());

                new Thread(new ConnectionHandler(socket, messages, sockets)).start();
                sockets.add(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ConnectionHandler implements Runnable {
    private Socket socket;
    private ArrayList<String> messages;
    private ArrayList<Socket> sockets;
    DataInputStream in;
    DataOutputStream out;

    public ConnectionHandler(Socket socket, ArrayList<String> messages, ArrayList<Socket> sockets) {
        this.socket = socket;
        this.messages = messages;
        this.sockets = sockets;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            for (String message : messages) {
                out.writeUTF(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                System.out.println("Received message: " + message + " from: " + socket.getInetAddress());
                if (message.equals("PING")) {
                    System.out.println("Sending response: PONG to: " + socket.getInetAddress());
                    out.writeUTF("PONG");
                    continue;
                }

                messages.add(message);

                for (Socket s : sockets) {
                    System.out.println("Sending message to: " + s);
                    DataOutputStream allOut = new DataOutputStream(s.getOutputStream());
                    allOut.writeUTF(message);
                }
            }
        } catch (EOFException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
            sockets.remove(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}