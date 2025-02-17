package tasks;

public class Task {
    protected Integer taskID;
    protected String taskName;
    protected String taskDescription;
    protected Status status;

    public Task(String taskName, String taskDescription, Status status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public Task(int taskID, String taskName, String taskDescription, Status status) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        if (this.taskName == null) {
            this.taskName = taskName;
        }
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        if (this.taskDescription == null) {
            this.taskDescription = taskDescription;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getID() {
        return this.taskID;
    }

    public void setID(int taskID) {
        if (this.taskID == null) {
            this.taskID = taskID;
        }
    }

    public String taskToSting(int taskID) {
        // 1,TASK,Task1,NEW,Description task1,
        return taskID + "," + TaskType.TASK + "," + getTaskName() + "," + getStatus() + "," + getTaskDescription() + ",";
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
