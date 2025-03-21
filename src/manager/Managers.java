package manager;

import manager.file.FileBackedTaskManager;
import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import manager.memory.InMemoryTaskManager;

import java.io.File;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return new FileBackedTaskManager();
    }

    public static InMemoryTaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefault(File savesTasks) {
        return new FileBackedTaskManager(savesTasks);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
