package manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;
    protected Task task;
    protected SubTask subTask;
    protected Epic epic;

    protected Integer task1, task2;
    protected Integer epic1;
    protected Integer subTask1, subTask2, subTask3;

    public void initTask() {

        task1 = taskManager.addNewTask(new Task("Задача 1", "01.01.2025 12:00", 30, "Добавили задачу 1", Status.NEW));
        task2 = taskManager.addNewTask(new Task("Задача 1", "01.02.2025 12:12", 45, "Добавили задачу 1", Status.NEW));
        epic1 = taskManager.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        subTask1 = taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "01.03.2025 11:11", 200, "Добавили ПодЗадача 1", epic1, Status.NEW));
        subTask2 = taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "02.03.2025 12:12", 622, "Добавили ПодЗадача 2", epic1, Status.NEW));
        subTask3 = taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "03.03.2025 13:13", 1535, "Добавили ПодЗадача 3", epic1, Status.NEW));

    }

    @Test
    void getTasks() {
        List<Task> tasks = taskManager.getTasksList();
        assertEquals(2,tasks.size(),"Количество задач в списке не 2");
    }

    @Test
    void getSubTasks() {
        List<SubTask> subTasks = taskManager.getSubTasksList();
        assertEquals(3,subTasks.size(),"Количество подзадач в списке  не 3");
    }

    @Test
    void getEpicsList() {
        List<Epic> epicList = taskManager.getEpicsList();
        assertEquals(1,epicList.size(),"Количество эпиков в списке не 1");
    }


/* ДОБАВИТЬ ТЕСТЫ
*/

    @DisplayName("расчёт статуса Epic: Граничные условия: Все подзадачи со статусом NEW.")
    @Test
    void checkEpicStatusWhenAllInNew(){
        assertEquals(Status.NEW, taskManager.getEpicByID(epic1).getStatus(),"Статус не " + Status.NEW
                + ". текущий статус эпика " + taskManager.getEpicByID(epic1).getStatus());
    }

    @DisplayName("расчёт статуса Epic: Граничные условия: Все подзадачи со статусом DONE.")
    @Test
    void checkEpicStatusWhenAllInDone(){
        taskManager.setSubTaskStatus(subTask1,Status.DONE);
        taskManager.setSubTaskStatus(subTask2,Status.DONE);
        taskManager.setSubTaskStatus(subTask3,Status.DONE);
        assertEquals(Status.DONE, taskManager.getEpicByID(epic1).getStatus(),"Статус не " + Status.DONE
                + ". текущий статус эпика " + taskManager.getEpicByID(epic1).getStatus());
    }


    @DisplayName("расчёт статуса Epic: Граничные условия: Подзадачи со статусами NEW и DONE.")
    @Test
    void checkEpicStatusWhenMixedNewAndDone(){
        taskManager.setSubTaskStatus(subTask1,Status.DONE);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicByID(epic1).getStatus(),"Статус не " + Status.IN_PROGRESS
                + ". текущий статус эпика " + taskManager.getEpicByID(epic1).getStatus());
    }

    @DisplayName("расчёт статуса Epic: Граничные условия: Подзадачи со статусами IN_PROGRESS.")
    @Test
    void checkEpicStatusWhenAllInProgress(){
        taskManager.setSubTaskStatus(subTask1,Status.IN_PROGRESS);
        taskManager.setSubTaskStatus(subTask2,Status.IN_PROGRESS);
        taskManager.setSubTaskStatus(subTask3,Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicByID(epic1).getStatus(),"Статус не " + Status.IN_PROGRESS
                + ". текущий статус эпика " + taskManager.getEpicByID(epic1).getStatus());
    }

}