package manager;

import tasks.Task;

import java.util.Comparator;

public class CompareByStartTime implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {

        if (task1 == null) {
            return 0;
        }
        if (task1.getStartTime() != null && task2.getStartTime() != null) {
            if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return 1;
            } else if ((task1.getStartTime().isBefore(task2.getStartTime()))) {
                return -1;
            }
        }
        return 0;
    }
}