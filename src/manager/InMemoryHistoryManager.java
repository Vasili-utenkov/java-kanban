package manager;

import ñlass.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final int MAX_COUNT_TASKS_IN_HISTORY = 10;
    private List<Task> historyManager = new ArrayList<>();

    @Override
    public void addTask(Task task) {

        if (historyManager.contains(task)) {
            historyManager.remove(historyManager.indexOf(task));
        } else
        if (historyManager.size() >= MAX_COUNT_TASKS_IN_HISTORY) {
            historyManager.remove(0);
        }

        historyManager.add(task);
    }

    @Override
    public void remove(int id) {
//        if (historyManager.contains(task)) {
//            historyManager.remove(historyManager.indexOf(task));
//        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = historyManager;
        return history;
    }
}
