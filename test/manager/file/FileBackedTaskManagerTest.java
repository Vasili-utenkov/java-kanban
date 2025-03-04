package manager.file;

import manager.Managers;
import manager.TaskManager;
import manager.TaskManagerTest;
import manager.file.FileBackedTaskManager;
import org.junit.jupiter.api.*;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    //taskManager = new FileBackedTaskManager();

    public File savesTasks = new File("src/Tasks.csv");
    private final FileBackedTaskManager taskManager = new FileBackedTaskManager(savesTasks);


//    static {
//        try {
//            savesTasks = File.createTempFile("Tasks", ".tmp", new File("D:\\JavaCourse"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @BeforeEach
//    public void setUp () throws IOException {
//        savesTasks = File.createTempFile("Tasks", "tmp", new File("D:\\JavaCourse"));
//        taskManager = new FileBackedTaskManager(savesTasks);
//        initTask();
//    }
//
//    @AfterEach
//    public void deleteFile () {
//        savesTasks.deleteOnExit();
//    }


    @Test
    void loadFromFile() {
        taskManager.loadTasksFromFile(savesTasks);

        taskManager.getTasksList();

        taskManager.getEpicsList();

        taskManager.getSubTasksList();

    }


//
// Иначе получал "Cannot invoke "manager.TaskManager.getEpicsList()" because "this.taskManager" is null"
// Готов выслушать напставления и инструкции как с этим бороться
//

    @Test
    void getSubTasks() {    }

    @Test
    void getTasks() {    }

    @Test
    void getEpicsList() {    }

    @Test
    void checkEpicStatusWhenAllInNew(){    }

    @Test
    void checkEpicStatusWhenAllInDone(){    }

    @Test
    void checkEpicStatusWhenMixedNewAndDone(){    }

    @Test
    void checkEpicStatusWhenAllInProgress(){    }

}