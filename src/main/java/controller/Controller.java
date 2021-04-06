package controller;

import model.Scheduler;
import model.SimulationManager;
import view.SimulationFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Controller {
    private static final ArrayList<Rectangle> rectangles = new ArrayList<>();
    private BufferedImage casier;

    public Controller(SimulationFrame frame, SimulationManager simulationManager) {
        try {
            casier = ImageIO.read(new File("casier.png"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        frame.addSimulationListener(e -> {
            simulationManager.setTimeLimit(frame.getSimulationTime());
            simulationManager.setNumberOfServers(frame.getNumberOfQueue());
            simulationManager.setNumberOfClients(frame.getNumberOfClients());
            simulationManager.setMinArrivalTime(frame.getArrivalTimeMin());
            simulationManager.setMaxArrivalTime(frame.getArrivalTimeMax());
            simulationManager.setMinProcessingTime(frame.getServiceTimeMin());
            simulationManager.setMaxProcessingTime(frame.getServiceTimeMax());
            Scheduler scheduler = new Scheduler(frame.getNumberOfQueue(), frame.getNumberOfClients());
            drawQueue(frame);
            frame.getPanel2().setRectangles(rectangles);
            simulationManager.setScheduler(scheduler);
            simulationManager.generateNRandomTasks();
            try {
                Thread simThread = new Thread(simulationManager);
                simThread.start();
                simThread.join();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public static ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public void drawQueue(SimulationFrame frame) {
        int i = 0;
        int k = 0;
        for (int j = 0; j < frame.getNumberOfQueue(); j++) {
            if (30 + (i * 120) > 730) {
                i = 0;
                k = k + 60;
            }
            Rectangle rectangle = new Rectangle(20 + (i * 120), 200 - k, 50, 50);
            i++;
            rectangles.add(rectangle);
            frame.getPanel2().getGraphics().drawImage(casier, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
        }
    }

}
