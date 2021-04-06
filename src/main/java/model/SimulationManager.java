package model;

import controller.Controller;
import view.SimulationFrame;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SimulationManager implements Runnable {
    private final Random random = new Random();
    private final SimulationFrame frame;
    private File outFile;
    private int timeLimit = 100;
    private int maxProcessingTime = 10;
    private int minProcessingTime = 2;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfServers = 3;
    private int numberOfClients = 100;
    private final List<Task> generatedTasks = new ArrayList<>(numberOfClients);
    private Scheduler scheduler;
    private BufferedImage client;
    private BufferedImage client1;
    private static  double avg=1;
    private double max=0;

    public SimulationManager(File outFile, SimulationFrame frame) {
        try {
            client = ImageIO.read(new File("client.png"));
            client1 = ImageIO.read(new File("client1.png"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.frame = frame;
        createFile(outFile);
        scheduler = new Scheduler(this.numberOfServers, this.numberOfClients);
    }

    public static void main(String[] args) {
        SimulationFrame frame = new SimulationFrame();
        SimulationManager simulationManager = new SimulationManager(new File("out.txt"), frame);
        Controller controller = new Controller(frame, simulationManager);
        frame.setVisible(true);
    }

    public void createFile(File outFile) {
        this.outFile = outFile;
        try {
            this.outFile.createNewFile();
        } catch (Exception ex) {
            System.out.println("Error: file");
        }
    }

    public void setScheduler(Scheduler scheduler1) {
        this.scheduler = scheduler1;
    }

    public int randomProcessing() {
        return random.ints(minProcessingTime, maxProcessingTime).findFirst().getAsInt();
    }

    public int randomArrival() {
        return random.ints(minArrivalTime, maxArrivalTime).findFirst().getAsInt();
    }

    public void generateNRandomTasks() {
        IntStream.range(0, numberOfClients).mapToObj(i -> new Task(randomArrival(), randomProcessing(), i)).forEach(this.generatedTasks::add);
        Collections.sort(this.generatedTasks);
    }

    @Override
    public void run() {
        int currentTime = 0;
        FileWriter file;
        try {
            file = new FileWriter(this.outFile.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }
        int avgS=averageService();
        int peak= peakHour();
        while (currentTime < timeLimit) {
            removeGeneratedTasks(currentTime,avg);
            updateGui();
            String text = currentTimePrint(currentTime);
            writeFile(file, text);
            frame.getPanel2().repaint();
            if (generatedTasks.isEmpty() && allClosed()) break;
            currentTime++;
            putThreadToSleep();
        }
        String printAverage= "Average waiting time: " + max/getNrClient();
        String printAverageS= "\nAverage service time: " + avgS/getNrClient();
        String printPeak= "\nPeak hour: " + peak/getNrClient();
        writeFile(file,printAverage);
        writeFile(file,printAverageS);
        writeFile(file, printPeak);
        closeFile(file);
        setOpenFalse();
    }


    public int getNrClient(){
        int sum=0;
        if(allClosed()){
            return numberOfClients;
        }else{
            for(int i=0; i<scheduler.getServers().size(); i++){
               sum+=scheduler.getServers().get(i).getTasks().size();
            }
            return numberOfClients- sum;
        }
    }
    public int averageService(){
        int sum=0;
    for(int i=0; i<generatedTasks.size(); i++){
        sum+=generatedTasks.get(i).getProcessingTime();
    }
    return sum;
    }
    public int peakHour(){
        int sum=0;
        for(int i=0; i<generatedTasks.size(); i++){
            sum+=generatedTasks.get(i).getArrivalTime();
        }
        return sum;
    }
    public boolean allClosed() {
        int j = 0;
        for (int i = 0; i < scheduler.getServers().size(); i++) {
            if (!scheduler.getServers().get(i).isOpen()) {
                j++;
            }
        }
        if (j == scheduler.getServers().size())
            return true;
        return false;
    }

    public void removeGeneratedTasks(int currentTime, double avg) {
        int i = 0;
        while (i < generatedTasks.size())
            if (generatedTasks.get(i).arrivalTime != currentTime) i++;
            else {
                scheduler.dispatchTask(generatedTasks.get(i));
                 if(max<avg)
                     max=avg;
                 avg+= scheduler.getServers().get(scheduler.minimWaitingPeriodQueue()).getWaitingPeriod()-currentTime;
                generatedTasks.remove(i);
            }
    }

    public void updateGui() {
        for (int k = 0; k < scheduler.getServers().size(); k++) {
            double x = Controller.getRectangles().get(k).getX();
            double y = Controller.getRectangles().get(k).getY();
            if (scheduler.getServers().get(k).isOpen()) {
                frame.getPanel2().getGraphics().clearRect((int) x + 60, (int) y - 10, 60, 60);
                String str = "(" + scheduler.getServers().get(k).getTasks().peek().getId() + ", " + scheduler.getServers().get(k).getTasks().peek().getArrivalTime() + ", " + scheduler.getServers().get(k).getTasks().peek().getProcessingTime() + ")";
                frame.getPanel2().getGraphics().drawString(str, (int) x + 60, (int) y - 1);
                if (scheduler.getServers().get(k).getTasks().peek().getProcessingTime() == 1)
                    frame.getPanel2().getGraphics().drawImage(client1, (int) x + 60, (int) y, 40, 50, null);
                else frame.getPanel2().getGraphics().drawImage(client, (int) x + 60, (int) y, 60, 50, null);
            } else {
                frame.getPanel2().getGraphics().clearRect((int) x + 60, (int) y - 10, 60, 60);
            }
        }
    }


    public void setOpenFalse() {
        for (Server q : scheduler.getServers()) {
            q.setOpen(false);
        }
    }

    public void closeFile(FileWriter file) {
        try {
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void putThreadToSleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeFile(FileWriter file, String text) {
        try {
            file.write(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public String currentTimePrint(int currentTime) {
        String text = "\nTime: " + currentTime + "\n";
        text += "Waiting clients: ";
        for (Task i : generatedTasks) text = new StringBuilder().append(text).append(i.toString()).toString();
        text = text + "\n" + scheduler.toString();
        return text;
    }
}
