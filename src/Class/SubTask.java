package Class;

import java.util.HashMap;

import Methods.*;


public class SubTask extends Task{
    protected static int counter = 0;
    protected int subTaskID;
    protected int epicID;

    public SubTask() {
    }

    public SubTask(int epicID, String taskName, String taskDescription) {
        super(taskName, taskDescription);
        counter ++;
        this.subTaskID = counter;
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }
}
