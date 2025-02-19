package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTaskIDList;


    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription, Status.NEW);
        this.subTaskIDList = new ArrayList<>();
    }

    public Epic(int taskID, String taskName, String taskDescription) {
        super(taskID, taskName, taskDescription, Status.NEW);
        this.subTaskIDList = new ArrayList<>();
    }


    // a. получение
    public ArrayList<Integer> getEpicSubtasks() {
        return new ArrayList<>(this.subTaskIDList);
    }

    public void addSubTaskID(int subTaskID) {
        this.subTaskIDList.add(subTaskID);
    }

    public void deleteSubTaskID(int subTaskID) { // FIX
        subTaskIDList.remove(Integer.valueOf(subTaskID));
    }

    public void clearSubTaskList() {
        this.subTaskIDList.clear();
    }


    public String taskToSting(int taskID) {
        // 2,EPIC,Epic2,DONE,Description epic2,
        return taskID + "," + TaskType.EPIC + "," + getTaskName() + "," + getStatus() + "," + getTaskDescription() + ",";
    }


    @Override
    public String toString() {
        return "Epic{" +
                "  ID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", subTaskIDList=" + subTaskIDList +
                '}' + '\n';
    }
}
