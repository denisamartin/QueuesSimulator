package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SimulationFrame extends JFrame {

    private JPanel contentPane;
    private JLabel arrivalTimeLabel;
    private JTextField arrivalTimeMin;
    private JLabel nbOfClientsLabel;
    private JTextField numberOfClients;
    private JTextField arrivalTimeMax;
    private JTextField numberOfQueues;
    private JTextField serviceTimeMin;
    private JTextField simulationInterval;
    private JTextField serviceTimeMax;
    private JLabel lblNumberOfQueues;
    private JLabel lblServiceTime;
    private JLabel lblSimulationInterval;
    private JLabel lblQueuesSimulator;
    private JButton btnSimulate;
    private JPanel panel1;
    private Panel2 panel2;
    private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

    public SimulationFrame() {
        this.setTitle("Queues Simulator");
        Color color=new Color(230, 164, 28);
        Color color2=new Color(241, 205, 133);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 823, 510);
        contentPane = new JPanel();
        contentPane.setBackground(color);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel1 = new JPanel();
        panel1.setBackground(new Color(245, 231, 202));
        panel1.setBounds(10, 10, 789, 153);
        contentPane.add(panel1);
        panel1.setLayout(null);

        arrivalTimeLabel = new JLabel("Arrival time ");
        arrivalTimeLabel.setForeground(color);
        arrivalTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        arrivalTimeLabel.setBounds(10, 72, 131, 23);
        panel1.add(arrivalTimeLabel);

        arrivalTimeMin = new JTextField();
        arrivalTimeMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        arrivalTimeMin.setColumns(10);
        arrivalTimeMin.setBackground(color2);
        arrivalTimeMin.setBounds(132, 74, 111, 19);
        panel1.add(arrivalTimeMin);

        nbOfClientsLabel = new JLabel("Number of clients ");
        nbOfClientsLabel.setForeground(color);
        nbOfClientsLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        nbOfClientsLabel.setBounds(10, 39, 123, 25);
        panel1.add(nbOfClientsLabel);

        numberOfClients = new JTextField();
        numberOfClients.setFont(new Font("Tahoma", Font.PLAIN, 15));
        numberOfClients.setColumns(10);
        numberOfClients.setBackground(color2);
        numberOfClients.setBounds(132, 41, 111, 19);
        panel1.add(numberOfClients);

        arrivalTimeMax = new JTextField();
        arrivalTimeMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
        arrivalTimeMax.setColumns(10);
        arrivalTimeMax.setBackground(color2);
        arrivalTimeMax.setBounds(261, 74, 111, 19);
        panel1.add(arrivalTimeMax);

        numberOfQueues = new JTextField();
        numberOfQueues.setFont(new Font("Tahoma", Font.PLAIN, 15));
        numberOfQueues.setColumns(10);
        numberOfQueues.setBackground(color2);
        numberOfQueues.setBounds(407, 41, 111, 19);
        panel1.add(numberOfQueues);

        serviceTimeMin = new JTextField();
        serviceTimeMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        serviceTimeMin.setColumns(10);
        serviceTimeMin.setBackground(color2);
        serviceTimeMin.setBounds(537, 76, 111, 19);
        panel1.add(serviceTimeMin);

        simulationInterval = new JTextField();
        simulationInterval.setFont(new Font("Tahoma", Font.PLAIN, 15));
        simulationInterval.setColumns(10);
        simulationInterval.setBackground(color2);
        simulationInterval.setBounds(668, 43, 111, 19);
        panel1.add(simulationInterval);

        serviceTimeMax = new JTextField();
        serviceTimeMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
        serviceTimeMax.setColumns(10);
        serviceTimeMax.setBackground(color2);
        serviceTimeMax.setBounds(668, 76, 111, 19);
        panel1.add(serviceTimeMax);

        lblNumberOfQueues = new JLabel("Number of queues ");
        lblNumberOfQueues.setForeground(color);
        lblNumberOfQueues.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNumberOfQueues.setBounds(261, 39, 136, 23);
        panel1.add(lblNumberOfQueues);

        lblServiceTime = new JLabel("ServiceTime");
        lblServiceTime.setForeground(color);
        lblServiceTime.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblServiceTime.setBounds(407, 72, 111, 23);
        panel1.add(lblServiceTime);

        lblSimulationInterval = new JLabel("Simulation interval");
        lblSimulationInterval.setForeground(color);
        lblSimulationInterval.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSimulationInterval.setBounds(537, 39, 131, 23);
        panel1.add(lblSimulationInterval);

        lblQueuesSimulator = new JLabel("QUEUES SIMULATOR ");
        lblQueuesSimulator.setForeground(color);
        lblQueuesSimulator.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblQueuesSimulator.setBounds(315, 3, 173, 23);
        panel1.add(lblQueuesSimulator);

        btnSimulate = new JButton("Simulate");
        btnSimulate.setForeground(new Color(255, 250, 250));
        btnSimulate.setBackground(color);
        btnSimulate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSimulate.setBounds(336, 120, 117, 23);
        panel1.add(btnSimulate);
        panel2=new Panel2();
        contentPane.add(panel2);

    }


    public int getSimulationTime() {
        return Integer.parseInt(this.simulationInterval.getText());
    }

    public int getNumberOfClients() {
        return Integer.parseInt(this.numberOfClients.getText());
    }

    public int getNumberOfQueue() {
        return Integer.parseInt(this.numberOfQueues.getText());
    }

    public int getArrivalTimeMin() {
        return Integer.parseInt(this.arrivalTimeMin.getText());
    }

    public int getArrivalTimeMax() {
        return Integer.parseInt(this.arrivalTimeMax.getText());
    }

    public int getServiceTimeMin() {
        return Integer.parseInt(this.serviceTimeMin.getText());
    }

    public int getServiceTimeMax() {
        return Integer.parseInt(this.serviceTimeMax.getText());
    }

    public Panel2 getPanel2() {
        return panel2;
    }

    public void addSimulationListener(ActionListener action) {
        this.btnSimulate.addActionListener(action);
    }


}