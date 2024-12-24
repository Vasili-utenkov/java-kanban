package Class;

public class Task {
    static int counter = 0;
    protected int taskID;
    protected String taskName;
    protected String taskDescription;
    protected Status status;

    public Task() {
    }


    public Task(String taskName, String taskDescription) {
        counter++;
        this.taskID = counter;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = Status.NEW;
    }



    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                '}';
    }
}
