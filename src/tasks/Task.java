package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Task {
    // формат для startTime
    public static final DateTimeFormatter START_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    // формат для duration
    protected Integer taskID;
    protected String taskName;
    protected String taskDescription;
    protected Status status;
    protected Optional<LocalDateTime> startTime;
    protected Optional<Duration> duration;


    public Task(String taskName, String startTimeString, Integer durationMinutes, String taskDescription, Status status) {
        this.taskName = taskName;
        if (startTimeString != null) {
            this.startTime = Optional.ofNullable(LocalDateTime.parse(startTimeString, START_TIME_FORMAT));
        } else {
            this.startTime = Optional.empty();
        }

        if (durationMinutes != null) {
            this.duration = Optional.ofNullable(Duration.ofMinutes(durationMinutes));
        } else {
            this.duration = Optional.empty();
        }

        this.taskDescription = taskDescription;
        this.status = status;
    }

    public Task(int taskID, String taskName, String startTimeString, Integer durationMinutes, String taskDescription, Status status) {
        this.taskID = taskID;
        this.taskName = taskName;
        if (startTimeString != null) {
            this.startTime = Optional.of(LocalDateTime.parse(startTimeString, START_TIME_FORMAT));
        } else {
            this.startTime = Optional.empty();
        }

        if (durationMinutes != null) {
            this.duration = Optional.ofNullable(Duration.ofMinutes(durationMinutes));
        } else {
            this.duration = Optional.empty();
        }

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

    // Запрос продолжительности задачи
    public Optional<Duration> getDuration() {
        return duration;
    }

    // Установка продолжительности задачи
    public void setDuration(Optional<Duration> duration) {
        this.duration = duration;
    }

    // Запрос начала работы
    public Optional<LocalDateTime> getStartTime() {
        return startTime;
    }

    // Установка начала работы
    public void setStartTime(Optional<LocalDateTime> startTime) {
        this.startTime = startTime;
    }

    // Запрос конца работы
    public Optional<LocalDateTime> getEndTime() {
        Optional<LocalDateTime> endTime = Optional.empty();
        if (startTime.isPresent() && duration.isPresent()) {
            endTime = Optional.ofNullable(startTime.get().plus(duration.get()));
        }
        return endTime;
    }


    // перевод старта задачи в стринг
    String getStartTimeInString(Optional<LocalDateTime> startTime) {
        String string = "";
        if (startTime.isPresent()) {
            string = START_TIME_FORMAT.format(startTime.get());
        }
        return string;
    }


    // перевод длительности задачи в стринг
    String getDurationInString(Optional<Duration> duration) {
        String string = "";
        if (duration.isPresent()) {
            string = String.valueOf(duration.get().toMinutes());
        }
        return string;
    }


    public String taskToSting(int taskID) {
//    "id,type,name,startAt,duration,status,description,epic"
        return taskID + "," + TaskType.TASK + "," + getTaskName()
                + "," + getStartTimeInString(startTime)
                + "," + getDurationInString(duration) // duration.get().toMinutes()
                + "," + getStatus() + "," + getTaskDescription() + ",";
    }

    // Для вывода в toString


    @Override
    public String toString() {
        Optional<LocalDateTime> endTime = Optional.empty();
        if (startTime.isPresent()) {
            endTime = Optional.of(startTime.get().plus(duration.get()));
        }


        return "Task{" + '\'' +
                "  ID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", startTime='" + getStartTimeInString(startTime) + '\'' +
                ", endTime='" + getStartTimeInString(endTime) + '\'' +
                ", duration='" + getDurationInString(duration) + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                '}' + '\n';
    }
}
