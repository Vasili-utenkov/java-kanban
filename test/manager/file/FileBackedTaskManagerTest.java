package manager.file;

import manager.Managers;
import manager.TaskManager;
import manager.TaskManagerTest;
import manager.file.FileBackedTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    public static File savesTasks;


    static {
        try {
            savesTasks = File.createTempFile("Tasks", ".tmp", new File("D:\\JavaCourse"));
//                savesHistory = File.createTempFile("History", "tmp", new File("D:\\JavaCourse"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp () throws IOException {
        savesTasks = File.createTempFile("Tasks", "tmp", new File("D:\\JavaCourse"));
        taskManager = new FileBackedTaskManager(savesTasks);
//        savesHistory = File.createTempFile("History", "tmp", new File("D:\\JavaCourse"));
//        taskManager.setFiles(savesTasks);
        initTask();
    }

    @AfterEach
    public void deleteFile () {
        savesTasks.deleteOnExit();
//        savesHistory.deleteOnExit();
    }


        //final TaskManager taskManager = Managers.getDefault(savesTasks);


//    private final TaskManager taskManager = Managers.getDefault();
//    private final HistoryManager historyManager = Managers.getDefaultHistory();


/*
        @BeforeEach
        public void setUp () throws IOException {
        savesTasks = File.createTempFile("Tasks", "tmp", new File("D:\\JavaCourse"));
        taskManager = new FileBackedTaskManager(savesTasks);
//        savesHistory = File.createTempFile("History", "tmp", new File("D:\\JavaCourse"));
//        taskManager.setFiles(savesTasks);
        initTask();
    }

        @AfterEach
        public void deleteFile () {
        savesTasks.deleteOnExit();
//        savesHistory.deleteOnExit();
    }
*/


/*
    @BeforeEach
    public void setUp() {
        file = new File("test_" + System.nanoTime() + ".csv");
        taskManager = new FileBackedTaskManager(file);
        initTask();
    }
*/

        @AfterEach
        void tearDown () {
        assertTrue(savesTasks.delete());
    }


        @Test
        void loadFromFile () {
        taskManager.loadTasksFromFile(savesTasks);
    }

    }