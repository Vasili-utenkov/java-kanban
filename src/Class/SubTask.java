package Class;

import java.util.HashMap;

import Methods.*;


public class SubTask extends Task {
    protected int epicID;

    public SubTask() {
    }

    public SubTask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        this.epicID = 0;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }


    public Status getSubTaskStatus() {
        return this.status;
    }



    @Override
    public String toString() {
        return "SubTask{" +
                "  ID=" + ID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", epicID=" + epicID +
                '}' + '\n';
    }


}
