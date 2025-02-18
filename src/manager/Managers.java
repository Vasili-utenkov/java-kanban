package manager;

import java.io.File;

public class Managers {

    private Managers() {

    }

    public static TaskManager getDefault() {
        return new FileBackedTaskManager();
    }

    public static TaskManager getDefault(File savesTasks, File savesHistory) {
        return new FileBackedTaskManager(savesTasks);
    }


    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}
