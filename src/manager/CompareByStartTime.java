package manager;

import tasks.Task;

import java.util.Comparator;

public class CompareByStartTime implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        if (task1 == null) {return 0;}
        if (task1.getStartTime().isPresent() && task2.getStartTime().isPresent()) {
            if (task1.getStartTime().get().isAfter(task2.getStartTime().get())) {
                return 1;
            } else if ((task1.getStartTime().get().isBefore(task2.getStartTime().get()))) {
                return -1;
            }
        }
        return 0;
    }
}