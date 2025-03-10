package tasks;

import java.time.LocalDateTime;
import java.util.Optional;

public class SubTask extends Task {
    private final Integer epicID;

    public SubTask(String taskName, String startTimeString, Integer durationMinutes, String taskDescription, int epicID, Status status) {
        super(taskName, startTimeString, durationMinutes, taskDescription, status);
        this.epicID = epicID;
    }

    public SubTask(int taskID, String taskName, String startTimeString, Integer durationMinutes, String taskDescription, int epicID, Status status) {
        super(taskName, startTimeString, durationMinutes, taskDescription, status);
        this.epicID = epicID;
    }

    public Integer getEpicID() {
        return epicID;
    }


    public String taskToSting(int taskID) {
//    "id,type,name,startAt,duration,status,description,epic"
        return taskID + "," + TaskType.SUBTASK + "," + getTaskName()
                + "," + getStartTimeInString(startTime)
                + "," + getDurationInString(duration) // duration.get().toMinutes()
                + "," + getStatus() + "," + getTaskDescription() + "," + getEpicID();
    }


    @Override
    public String toString() {
        Optional<LocalDateTime> endTime = Optional.empty();
        if (startTime.isPresent()) {
            endTime = Optional.of(startTime.get().plus(duration.get()));
        }

        return "SubTask{" + '\'' +
                "  ID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", startTime='" + getStartTimeInString(startTime) + '\'' +
                ", endTime='" + getStartTimeInString(endTime) + '\'' +
                ", duration='" + getDurationInString(duration) + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", epicID=" + epicID +
                '}' + '\n';
    }


}
