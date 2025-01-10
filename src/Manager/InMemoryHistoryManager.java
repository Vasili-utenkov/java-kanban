package Manager;

import Class.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final int MAX_COUNT_TASKS_IN_HISTORY = 10;

    List<Task> historyManager = new ArrayList<>();

    @Override
    public void addTask(Task task) {

        if (historyManager.contains(task)) {
            historyManager.remove(historyManager.indexOf(task));
            historyManager.add(task);
        } else if (historyManager.size() < MAX_COUNT_TASKS_IN_HISTORY) {
            historyManager.add(task);
        } else {
            historyManager.remove(0);
            historyManager.add(task);
        }

    }

    @Override
    public List<Task> getHistory() {
        return historyManager;
    }
}
