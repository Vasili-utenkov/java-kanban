package tasks;

public class Task {
    protected int taskID;
    protected String taskName;
    protected String taskDescription;
    protected Status status;

    public Task(String taskName, String taskDescription, Status status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setID(int taskID) {
        this.taskID = taskID;
    }

    public int getID() {
        return this.taskID;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "  ID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                '}' + '\n';
    }
}
