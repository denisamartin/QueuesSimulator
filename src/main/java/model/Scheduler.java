package model;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final List<Server> servers;
    private final int maxNoServers;
    private final ArrayList<Thread> threads;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.servers = new ArrayList<>();
        this.threads = new ArrayList<>();
        for (int i = 0; i < maxNoServers; i++) {
            servers.add(new Server(maxTasksPerServer, i));
            threads.add(new Thread(servers.get(i)));
            threads.get(i).start();
        }
    }

    public int minimWaitingPeriodQueue() {
        int waitingPeriod = 9999;
        int queue = 0;
        int i = 0;
        while (i < maxNoServers && waitingPeriod != 0) {
            int currentWaitingPeriod = servers.get(i).getWaitingPeriod();
            if (currentWaitingPeriod < waitingPeriod) {
                waitingPeriod = currentWaitingPeriod;
                queue = i;
            }
            i++;
        }
        return queue;
    }

    public double getAvg() {
        int sum = 0;
        for (int i = 0; i < servers.size(); i++) {
            sum += servers.get(i).getWaitingPeriod();
        }
        return sum / (double)servers.size();
    }

    public void dispatchTask(Task t) {
        int minimPeriodQueue = minimWaitingPeriodQueue();
        servers.get(minimPeriodQueue).addTask(t);
        if (!servers.get(minimPeriodQueue).isOpen()) {
            servers.get(minimPeriodQueue).setOpen(true);
            threads.set(minimPeriodQueue, new Thread(servers.get(minimPeriodQueue)));
            threads.get(minimPeriodQueue).start();
        }
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Server i : servers) {
            StringBuilder queue = stringBuilder.append("Queue ").append(i.getServerId()).append(": ").append(i.toString()).append("\n");
        }
        return stringBuilder.toString();
    }


}
