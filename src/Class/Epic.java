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


    // a. Получение списка всех подзадач определённого эпика.
    public ArrayList<Integer> getSubTaskListID() {
        return this.subTaskIDList;
    }

    public void addSubTaskID(int subTaskID) {
        this.subTaskIDList.add(subTaskID);
    }

    public void deleteSubTaskID(int subTaskID) { // FIX
        for (int i = 0; i < subTaskIDList.size(); i++) {
            if (subTaskIDList.get(i) == subTaskID) {
                this.subTaskIDList.remove(i);
            }
        }
    }

    public void clearSubTaskList() {
        this.subTaskIDList.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "  ID=" + ID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", subTaskIDList=" + subTaskIDList +
                '}' + '\n';
    }
}
