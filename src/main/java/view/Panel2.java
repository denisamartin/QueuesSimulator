package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Panel2 extends JPanel {
    private Image background;
    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    public Panel2() {
        try {
            background = ImageIO.read(new File("background.png"));
        } catch (Exception exception) {

        }
        setBackground(new Color(244, 164, 96));
        setBounds(10, 173, 789, 290);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(ArrayList<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

}
