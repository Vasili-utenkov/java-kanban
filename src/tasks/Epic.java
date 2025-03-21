package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Epic extends Task {

    protected LocalDateTime endTime;
    private final ArrayList<Integer> subTaskIDList;

    public Epic(String taskName, String taskDescription) {
        super(taskName, LocalDateTime.now().format(START_TIME_FORMAT).toString(), 0, taskDescription, Status.NEW, false);

        this.subTaskIDList = new ArrayList<>();
        this.endTime = this.startTime;
    }

    public Epic(int taskID, String taskName, String taskDescription) {
        super(taskID, taskName, LocalDateTime.now().format(START_TIME_FORMAT).toString(), 0, taskDescription, Status.NEW, false);

        this.subTaskIDList = new ArrayList<>();
        this.endTime = this.startTime;
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


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String taskToSting(int taskID) {
//    "id,type,name,startAt,duration,status,description,epic"
        return taskID + "," + TaskType.EPIC + "," + getTaskName()
                + "," + getStartTimeInString(startTime)
                + "," + getDurationInString(duration) // duration.get().toMinutes()
                + "," + getStatus() + "," + getTaskDescription() + ",";
    }


    @Override
    public String toString() {
        return "Epic{" + '\'' +
                "  ID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", startTime='" + getStartTimeInString(startTime) + '\'' +
                ", endTime='" + getStartTimeInString(endTime) + '\'' +
                ", duration='" + getDurationInString(duration) + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", subTaskIDList=" + subTaskIDList +
                '}' + '\n';
    }
}
