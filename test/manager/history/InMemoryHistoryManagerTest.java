package manager.history;

import manager.memory.InMemoryTaskManager;
import manager.TaskManagerTest;
import org.junit.jupiter.api.BeforeEach;

class InMemoryHistoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
        initTask();
    }



}