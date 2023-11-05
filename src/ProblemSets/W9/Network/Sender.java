package ProblemSets.W9.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Sender {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8000)) {
            new Thread(new Input(socket, new ArrayList<String>())).start();
            new Thread(new Output(socket, new LinkedList<String>())).start();
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Input implements Runnable {
        private DataInputStream in;
        private ArrayList<String> messages;

        public Input(Socket socket, ArrayList<String> messages) throws Exception {
            this.in = new DataInputStream(socket.getInputStream());
            this.messages = messages;
        }

        public void run() {
            try {
                while (true) {
                    String message = in.readUTF();
                    System.out.println("Received message: " + message);
                    if (message.equals("PONG")) {
                        continue;
                    }
                    messages.add(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Output implements Runnable {
        private DataOutputStream out;
        public Queue<String> messages;

        public Output(Socket socket, Queue<String> messages) throws Exception {
            this.out = new DataOutputStream(socket.getOutputStream());
            this.messages = messages;
        }

        public void run() {
            int i = 0;
            try {
                while (true) {
                    String message = messages.poll();
                    if (message != null) {
                        out.writeUTF(message);
                        System.out.println("Sent message: " + message);
                    }

                    i++;
                    if ((i %= 1000000000) == 0) {
                        System.out.println("Sending PING");
                        out.writeUTF("PING");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            messages.add(message);
        }
    }
}