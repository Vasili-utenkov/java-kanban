package tasks;

import manager.history.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private final TaskManager taskManager = Managers.getDefault();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void isImmutableTask() {
        Task task = taskManager.getTaskByID(taskManager.addNewTask(new Task("Задача 1", "01.03.2025 10:10", 1, "Добавили задачу 1", Status.NEW)));

        int id = task.getID();
        String name = task.getTaskName();
        String description = task.getTaskDescription();

        task.setID(5);
        task.setTaskName("New");
        task.setTaskDescription("New");

        assertEquals(id, task.getID(), "Значение ID экземпляра класса Task изменяется");
        assertEquals(name, task.getTaskName(), "Значение Name экземпляра класса Task изменяется");
        assertEquals(description, task.getTaskDescription(), "Значение Description экземпляра класса Task изменяется");

        taskManager.deleteTask(id);


    }

}