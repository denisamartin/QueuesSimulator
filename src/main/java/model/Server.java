package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static java.lang.Thread.sleep;

public class Server implements Runnable {
    private final BlockingQueue<Task> tasks;
    private final int serverId;
    private int waitingPeriod;
    private boolean open = true;

    public Server(int maximTasks, int id) {
        this.tasks = new ArrayBlockingQueue<>(maximTasks);
        this.waitingPeriod = 0;
        this.serverId = id;
    }

    public void addTask(Task newTask) {
        this.tasks.add(newTask);
        this.waitingPeriod += newTask.getProcessingTime();
    }

    public void run() {
        while (open) {
            while (tasks.peek() != null) {
                updateProcessingTime();
            }
            this.open = false;
        }
    }

    public void updateProcessingTime() {
        assert tasks.peek() != null;
        if (tasks.peek().getProcessingTime() == 0) {
            tasks.poll();
        }else{
            assert tasks.peek() != null;
            int processingTime = tasks.peek().getProcessingTime();
            putThreadToSleep();
            assert tasks.peek() != null;
            tasks.peek().setProcessingTime(processingTime -1);
            waitingPeriod--;
        }
    }

    public void putThreadToSleep() {
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public int getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getServerId() {
        return serverId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String toString() {
        if (tasks.peek() == null || tasks.peek().getProcessingTime() == 0) {
            return "closed";
        } else return tasks.toString();
    }
}
