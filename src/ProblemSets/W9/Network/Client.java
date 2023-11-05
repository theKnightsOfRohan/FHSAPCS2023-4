package ProblemSets.W9.Network;

import processing.core.PApplet;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import ProblemSets.W9.Network.Sender.*;

public class Client extends PApplet {
    Socket socket = null;
    ArrayList<String> points;
    Queue<String> messages;
    Input input;
    Thread inputThread;
    Output output;
    Thread outputThread;

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        points = new ArrayList<String>();
        messages = new LinkedList<String>();
        try {
            socket = new Socket("127.0.0.1", 8000);
            System.out.println("Connected to server: " + socket);

            input = new Input(socket, points);
            output = new Output(socket, messages);

            inputThread = new Thread(input);
            outputThread = new Thread(output);

            inputThread.start();
            outputThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        background(200);
        fill(0);
        text("framerate: " + frameRate, 10, 10);
        fill(255);
        int size = points.size();
        for (int i = 0; i < size; i++) {
            String[] xy = points.get(i).split(",");
            ellipse(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]), 10, 10);
        }
    }

    public void mouseReleased() {
        output.sendMessage(mouseX + "," + mouseY);
    }

    public static void main(String[] args) {
        PApplet.main("ProblemSets.W9.Network.Client");
    }
}