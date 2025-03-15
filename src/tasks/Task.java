package tasks;

import manager.memory.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    // формат для startTime
    public static final DateTimeFormatter START_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    // формат для duration
    protected Integer taskID;
    protected String taskName;
    protected String taskDescription;
    protected Status status;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task(String taskName, String startTimeString, Integer durationMinutes, String taskDescription, Status status) {
        this(taskName, startTimeString, durationMinutes, taskDescription, status, true);
    }

    public Task(int taskID, String taskName, String startTimeString, Integer durationMinutes, String taskDescription, Status status) {
        this(taskID, taskName, startTimeString, durationMinutes, taskDescription, status, true);
    }

    public Task(String taskName, String startTimeString, Integer durationMinutes, String taskDescription, Status status, boolean checkInterception) {
        if (checkInterception) {
            if (startTimeString == null && durationMinutes == null) {
                return;
            }
        }

        this.taskName = taskName;
        this.startTime = (startTimeString != null) ? LocalDateTime.parse(startTimeString, START_TIME_FORMAT) : null;
        this.duration = (durationMinutes != null) ? Duration.ofMinutes(durationMinutes) : null;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public Task(int taskID, String taskName, String startTimeString, Integer durationMinutes, String taskDescription, Status status, boolean checkInterception) {
        if (checkInterception) {
            if (startTimeString == null && durationMinutes == null) {
                return;
            }
        }

        this.taskID = taskID;
        this.taskName = taskName;
        this.startTime = (startTimeString != null) ? LocalDateTime.parse(startTimeString, START_TIME_FORMAT) : null;
        this.duration = (durationMinutes != null) ? Duration.ofMinutes(durationMinutes) : null;
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
    public Duration getDuration() {
        return duration;
    }

    // Установка продолжительности задачи
    public void setDuration(Integer duration) {
        if (duration != null) {
            Duration newDuration = Duration.ofMinutes(duration);
            InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
            if (!inMemoryTaskManager.isInterceptTime(this.getStartTime(), newDuration)) {
                this.duration = newDuration;
            }
        } else {
            this.duration = null;
        }
    }

    // Запрос начала работы
    public LocalDateTime getStartTime() {
        return startTime;
    }

    // Установка начала работы
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // Запрос конца работы
    public LocalDateTime getEndTime() {
        LocalDateTime endTime = null;
        if (startTime != null && duration != null) {
            endTime = startTime.plus(duration);
        }
        return endTime;
    }


    // перевод старта задачи в стринг
    String getStartTimeInString(LocalDateTime startTime) {
        String string = "";
        if (startTime != null) {
            string = START_TIME_FORMAT.format(startTime);
        }
        return string;
    }


    // перевод длительности задачи в стринг
    String getDurationInString(Duration duration) {
        String string = "";
        if (duration != null) {
            string = String.valueOf(duration.toMinutes());
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
        LocalDateTime endTime = null;
        if (startTime != null) {
            endTime = startTime.plus(duration);
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
