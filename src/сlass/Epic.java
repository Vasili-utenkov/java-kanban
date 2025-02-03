package сlass;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTaskIDList;


    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription, Status.NEW);
        this.subTaskIDList = new ArrayList<>();
    }


    // a. Получение списка всех подзадач определённого эпика.
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
