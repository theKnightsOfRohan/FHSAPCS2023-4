package ProblemSets.W8;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import processing.core.PApplet;

public class D3 extends PApplet {
    ArrayList<Point> pointList;

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        pointList = new ArrayList<Point>();
    }

    public void draw() {
        drawAllPoints();
    }

    private void drawAllPoints() {
        for (Point point : pointList) {
            ellipse(point.getX(), point.getY(), 20, 20);
        }
    }

    public void keyReleased() {
        if (key == 's') {
            savePoints();
        } else if (key == 'l') {
            loadPoints();
        }
    }

    private void savePoints() {
        try (FileWriter writer = new FileWriter("src/ProblemSets/W8/points.json")) {
            JSONArray points = new JSONArray();
            for (Point point : pointList) {
                JSONObject pointJson = new JSONObject();
                pointJson.put("x", point.getX());
                pointJson.put("y", point.getY());
                points.put(pointJson);
            }
            writer.write(points.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPoints() {
        try (FileReader reader = new FileReader("src/ProblemSets/W8/points.json")) {
            String json = new String(Files.readAllBytes(Paths.get("src/ProblemSets/W8/points.json")));
            JSONArray points = new JSONArray(json);
            pointList.clear();
            for (int i = 0; i < points.length(); i++) {
                JSONObject pointJson = points.getJSONObject(i);
                pointList.add(new Point(pointJson.getFloat("x"), pointJson.getFloat("y")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mousePressed() {
        pointList.add(new Point(mouseX, mouseY));
    }

    public static void main(String[] args) {
        PApplet.main("ProblemSets.W8.D3");
    }
}

class Point {
    private float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
