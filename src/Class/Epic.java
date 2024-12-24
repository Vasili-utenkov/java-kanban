package Class;

import java.util.ArrayList;
import java.util.HashMap;

import Methods.*;

public class Epic extends Task {
    protected ArrayList<Integer> subTaskIDList;

    public Epic() {
    }

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        this.subTaskIDList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskListID() {
        return subTaskIDList;
    }

    public void addSubTaskID(int subTaskID) {
        subTaskIDList.add(subTaskID);
    }

    public void deleteSubTaskID(int subTaskID) {
        subTaskIDList.remove(subTaskID);
    }

    public void clearSubTaskList() {
        subTaskIDList.clear();
    }



}
