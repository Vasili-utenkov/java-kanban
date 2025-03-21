package manager.file;

import manager.TaskManagerTest;
import org.junit.jupiter.api.*;
import java.io.File;


import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    public File savesTasks = new File("src/Tasks.csv");
    private final FileBackedTaskManager taskManager = new FileBackedTaskManager(savesTasks);


    @Test
    void loadFromFile() {

        int taskCount = taskManager.getTasksList().size();
        assertEquals(1, taskCount, "Количество загруженных задач не совпадает"
                + " с сохраненными в файле. Ожидалось 2, загрузилось " + taskCount);

        int epicCount = taskManager.getEpicsList().size();
        assertEquals(1, epicCount,  "Количество загруженных эпиков не совпадает"
                + " с сохраненными в файле. Ожидалось 1, загрузилось "+ epicCount);

        int subTaskCount = taskManager.getSubTasksList().size();
        assertEquals(3, subTaskCount, "Количество загруженных подзадач не совпадает"
                + " с сохраненными в файле. Ожидалось 3, загрузилось " + subTaskCount);
    }


//
// Иначе получал "Cannot invoke "manager.TaskManager.getEpicsList()" because "this.taskManager" is null"
// Готов выслушать наставления и инструкции как с этим бороться
//

    @Test
    void getSubTasks() {
    }

    @Test
    void getTasks() {
    }

    @Test
    void getEpicsList() {
    }

    @Test
    void checkEpicStatusWhenAllInNew() {
    }

    @Test
    void checkEpicStatusWhenAllInDone() {
    }

    @Test
    void checkEpicStatusWhenMixedNewAndDone() {
    }

    @Test
    void checkEpicStatusWhenAllInProgress() {
    }

}