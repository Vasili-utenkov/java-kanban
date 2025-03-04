package manager.history;

import manager.memory.InMemoryTaskManager;
import manager.TaskManagerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InMemoryHistoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
        initTask();
    }


    @Test
    void getHistoryTest() {

        assertEquals(0,taskManager.getHistory().size(),"Количество задач в истории просмотов не 0, а " + taskManager.getHistory().size());

        taskManager.getSubTaskByID(subTask1);
        assertEquals(1,taskManager.getHistory().size(),"Количество задач в истории просмотов  не 1, а " + taskManager.getHistory().size());

        taskManager.getSubTaskByID(subTask2);
        assertEquals(2,taskManager.getHistory().size(),"Количество задач в истории просмотов  не 2, а " + taskManager.getHistory().size());

        int idInitial = taskManager.getHistory().indexOf(taskManager.getSubTaskByID(subTask1));
        taskManager.getSubTaskByID(subTask1);
        int idFinal = taskManager.getHistory().indexOf(taskManager.getSubTaskByID(subTask1));
        assertNotEquals(idInitial,idFinal,"Позиция в истории просмотров осталась прежней");

        taskManager.deleteSubTask(subTask3);
        int id = taskManager.getHistory().indexOf(taskManager.getSubTaskByID(subTask3));
        assertEquals(-1,id,"После удаления задача осталась в истории просмотра и имеет индекс " + id);

    }

}