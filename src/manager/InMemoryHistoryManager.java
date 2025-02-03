package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyManager = new ArrayList<>();

    @Override
    public void addTask(Task task) {

        if (historyManager.contains(task)) {
            historyManager.remove(historyManager.indexOf(task));
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
