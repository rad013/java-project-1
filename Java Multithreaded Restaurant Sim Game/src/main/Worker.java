package main;

public class Worker {
    private int workerCount = 0;
    private int workDuration;

    public Worker() {
	this.workerCount += 1;
	this.workDuration = 10;
    }

    public int getWorkerCount() {
	return workerCount;
    }

    public void setWorkerCount(int workerCount) {
	this.workerCount = workerCount;
    }

    public int getWorkDuration() {
	return workDuration;
    }

    public void setWorkDuration(int workDuration) {
	this.workDuration = workDuration;
    }

}
