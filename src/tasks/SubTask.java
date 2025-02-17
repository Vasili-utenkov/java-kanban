package tasks;

public class SubTask extends Task {
    private Integer epicID;

    public SubTask(String taskName, String taskDescription, int epicID, Status status) {
        super(taskName, taskDescription, status);
        this.epicID = epicID;
    }

    public SubTask(int taskID, String taskName, String taskDescription, int epicID, Status status) {
        super(taskID, taskName, taskDescription, status);
        this.epicID = epicID;
    }


    public SubTask(Task task, int epicID) {
        super(task.taskName, task.taskDescription, task.status);
        this.epicID = epicID;
    }

    public Integer getEpicID() {
        return epicID;
    }


    public String taskToSting(int taskID) {
        // 3,SUBTASK,Sub Task2,DONE,Description sub task3,2
        return taskID + "," + TaskType.SUBTASK + "," + getTaskName() + "," + getStatus() + "," + getTaskDescription() + "," + getEpicID();
    }


    @Override
    public String toString() {
        return "SubTask{" +
                "  ID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", epicID=" + epicID +
                '}' + '\n';
    }


}
