package tasks;

public class SubTask extends Task {
    private int epicID;

    public SubTask(String taskName, String taskDescription, int epicID, Status status) {
        super(taskName, taskDescription, status);
        this.epicID = epicID;
    }

    public SubTask(Task task, int epicID) {
        super(task.taskName, task.taskDescription, task.status);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
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
