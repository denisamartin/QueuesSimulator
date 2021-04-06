package model;

public class Task implements Comparable<Task> {
    int arrivalTime;
    int processingTime;
    int id;

    public Task(int arrivalTime, int processingTime, int id) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.id = id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getArrivingTime() { return this.arrivalTime; }

    public int getId() { return id; }

    @Override
    public int compareTo(Task task) {
        return this.getArrivingTime() - task.getArrivingTime();
    }

    public String toString() {
        return "(" + id + "," + arrivalTime + "," + processingTime + ")";
    }

}


