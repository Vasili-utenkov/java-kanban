package manager.file;

import manager.Managers;
import manager.TaskManager;
import manager.TaskManagerTest;
import manager.file.FileBackedTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

//    public static File savesTasks = new File("src/Tasks.csv");
//    private final FileBackedTaskManager taskManager = new FileBackedTaskManager(savesTasks);

    public File savesTasks;
    private FileBackedTaskManager taskManager;


/*
    static {
        try {
            savesTasks = File.createTempFile("Tasks", ".tmp", new File("D:\\JavaCourse"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp () throws IOException {
        savesTasks = File.createTempFile("Tasks", "tmp", new File("D:\\JavaCourse"));
        taskManager = new FileBackedTaskManager(savesTasks);
        initTask();
    }

    @AfterEach
    public void deleteFile () {
        savesTasks.deleteOnExit();
    }

*/

//    savesTasks = new File("src/Tasks.csv");
//    taskManager = new FileBackedTaskManager(savesTasks);
//    initTask();



    @BeforeAll
    public void setUp() {
        File savesTasks = new File("src/Tasks.csv");
        final FileBackedTaskManager taskManager = new FileBackedTaskManager(savesTasks);
        initTask();
    }


    @AfterEach
    void tearDown() {
        assertTrue(savesTasks.delete());
    }


    @Test
    void loadFromFile() {
        taskManager.loadTasksFromFile(savesTasks);
    }

}