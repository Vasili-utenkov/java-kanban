package manager.memory;

import manager.TaskManagerTest;
import org.junit.jupiter.api.*;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp(){
        taskManager = new InMemoryTaskManager();
        initTask();
    }



    //    проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @DisplayName("экземпляры класса Task равны друг другу, если равен их id")
    @Test
    void equalsTaskByID() {
        int taskID = taskManager.addNewTask(new Task("Задача для теста", "01.03.2025 10:10", 1, "Добавили задачу для теста", Status.NEW));
        Task taskToCompare1 = taskManager.getTaskByID(taskID);
        Task taskToCompare2 = taskManager.getTaskByID(taskID);
        assertNotNull(taskToCompare1, "1 экземпляр класса Task для сравнения равенства не существует");
        assertNotNull(taskToCompare2, "2 экземпляр класса Task для сравнения равенства не существует");
        assertEquals(taskToCompare1, taskToCompare2, "экземпляры класса Task не равны друг другу, если равен их id");
        taskManager.deleteTask(taskID);
    }


    @DisplayName("Epic равны друг другу, если равен их id")
    @Test
    void equalsEpicByID() {
        int epicID = taskManager.addNewEpic(new Epic("Эпик для теста", "Добавили Эпик"));
        Epic taskToCompare1 = taskManager.getEpicByID(epicID);
        Epic taskToCompare2 = taskManager.getEpicByID(epicID);
        assertNotNull(taskToCompare1, "1 экземпляр класса Epic для сравнения равенства не существует");
        assertNotNull(taskToCompare2, "2 экземпляр класса Epic для сравнения равенства не существует");
        assertEquals(taskToCompare1, taskToCompare2, "экземпляры класса Epic не равны друг другу, если равен их id");
        taskManager.deleteEpic(epicID);
    }


    //    проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @DisplayName("InMemoryTaskManager добавляет задачи разного типа и может найти их по id")
    @Test
    void isTaskManagerAddAndFindTasks() {
        int taskID = taskManager.addNewTask(new Task("Задача для теста", "01.03.2025 10:10", 1, "Добавили задачу для теста", Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task не найден по id");

        int epicID = taskManager.addNewEpic(new Epic("Эпик для теста", "Добавили эпик для теста"));
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic не найден по id");

        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача для теста", "01.03.2025 10:15", 1, "Добавили подзадачу для теста", epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic не найден по id");

        taskManager.deleteTask(taskID);
        taskManager.deleteSubTask(subTaskID);
        taskManager.deleteEpic(epicID);
    }


    //    создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @DisplayName("неизменность задачи (по всем полям) при добавлении задачи в менеджер")
    @Test
    void isEqualSavedTasks() {
        String taskName = "Задача";
        String taskDescription = "Описание задачи";
        String epicName = "Эпик";
        String epicDescription = "Описание эпика";
        String subTaskName = "Подзадача";
        String subTaskDescription = "Описание подзадачи";

        int taskID = taskManager.addNewTask(new Task(taskName, "01.03.2025 10:10", 10, taskDescription, Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task не найден по id");
        assertEquals(taskName, task.getTaskName(), "Названия задач не совпадают.");
        assertEquals(taskDescription, task.getTaskDescription(), "Описания задач не совпадают.");


        int epicID = taskManager.addNewEpic(new Epic(epicName, epicDescription));
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic не найден по id");
        assertEquals(epicName, epic.getTaskName(), "Названия эпиков не совпадают.");
        assertEquals(epicDescription, epic.getTaskDescription(), "Описания эпиков не совпадают.");


        int subTaskID = taskManager.addNewSubTask(new SubTask(subTaskName, "01.03.2025 10:25", 10, subTaskDescription, epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic не найден по id");
        assertEquals(subTaskName, subTask.getTaskName(), "Названия подзадач не совпадают.");
        assertEquals(subTaskDescription, subTask.getTaskDescription(), "Описания подзадач не совпадают.");

        taskManager.deleteTask(taskID);
        taskManager.deleteEpic(epicID);
        taskManager.deleteSubTask(subTaskID);
    }


    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @DisplayName("задачи, добавляемые в HistoryManager, сохраняют данные.")
    @Test
    void isEqualSavedInHistoryTasks() {
        int taskID = taskManager.addNewTask(new Task("Задача", "01.03.2025 10:10", 1, "Описание задачи", Status.NEW));
        Task task = taskManager.getTaskByID(taskID);

        int size = taskManager.getHistory().size();
        Task taskInHistory = taskManager.getHistory().get(size - 1);

        assertNotNull(task, "Task не найден по id");
        assertNotNull(taskInHistory, "Task не найден в истории");
        assertEquals(task.getTaskName(), taskInHistory.getTaskName(), "Названия задач не совпадают.");
        assertEquals(task.getTaskDescription(), taskInHistory.getTaskDescription(), "Описания задач не совпадают.");
        assertEquals(task.getStatus(), taskInHistory.getStatus(), "Статусы задач не совпадают.");


        int epicID = taskManager.addNewEpic(new Epic("Эпик", "Описание эпика"));
        Epic epic = taskManager.getEpicByID(epicID);
        size = taskManager.getHistory().size();
        Epic epicInHistory = (Epic) taskManager.getHistory().get(size - 1);

        assertNotNull(epic, "Epic не найден по id");
        assertNotNull(epicInHistory, "Epic не найден в истории");
        assertEquals(epic.getTaskName(), epicInHistory.getTaskName(), "Названия задач не совпадают.");
        assertEquals(epic.getTaskDescription(), epicInHistory.getTaskDescription(), "Описания задач не совпадают.");
        assertEquals(epic.getStatus(), epicInHistory.getStatus(), "Статусы задач не совпадают.");


        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача", "01.03.2025 10:15", 1, "Описание подзадачи", epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        size = taskManager.getHistory().size();
        SubTask subTaskInHistory = (SubTask) taskManager.getHistory().get(size - 1);

        assertNotNull(subTask, "Task не найден по id");
        assertNotNull(taskInHistory, "Task не найден в истории");
        assertEquals(subTask.getTaskName(), subTaskInHistory.getTaskName(), "Названия подзадач не совпадают.");
        assertEquals(subTask.getTaskDescription(), subTaskInHistory.getTaskDescription(), "Описания подзадач не совпадают.");
        assertEquals(subTask.getStatus(), subTaskInHistory.getStatus(), "Статусы подзадач не совпадают.");

        assertEquals(epic.getEpicSubtasks(), epicInHistory.getEpicSubtasks(), "Список позадач у эпиков не совпадают.");


        taskManager.deleteTask(taskID);
        taskManager.deleteEpic(epicID);
        taskManager.deleteSubTask(subTaskID);

    }

//    Удаляемые подзадачи не должны хранить внутри себя старые id.
    /*не реализовываются*/

    //    Внутри эпиков не должно оставаться неактуальных id подзадач.
    @DisplayName("Внутри эпиков не должно оставаться неактуальных id подзадач")
    @Test
    void isConsistDeletedSubTask() {

        Integer epicID = taskManager.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        Integer subTask1 = taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "01.01.2035 10:10", 1500, "Добавили ПодЗадача 1", epicID, Status.NEW));
        Integer subTask2 = taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "01.02.2035 10:10", 707, "Добавили ПодЗадача 2", epicID, Status.NEW));
        Integer subTask3 = taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "01.03.2035 10:10", 250, "Добавили ПодЗадача 3", epicID, Status.NEW));

        if (subTask1 != null) taskManager.deleteSubTask(subTask1);
        assertFalse(taskManager.getSubTaskList(epicID).contains(subTask1));

        if (subTask3 != null) taskManager.deleteSubTask(subTask3);
        assertFalse(taskManager.getSubTaskList(epicID).contains(subTask3));

        if (epicID != null) taskManager.deleteEpic(epicID);
        if (subTask2 != null) taskManager.deleteSubTask(subTask2);
    }

    @DisplayName("Проверка попадания и исключения задач в приоретизированный список задач")
    @Test
    void isInterceptTimeTest() {
/* Начальный
startTime='01.02.2025 12:12', endTime='01.02.2025 12:57', duration='45'
startTime='01.03.2025 11:11', endTime='01.03.2025 14:31', duration='200'
startTime='02.03.2025 12:12', endTime='02.03.2025 22:34', duration='622'
startTime='03.03.2025 13:13', endTime='04.03.2025 14:48', duration='1535'
*/

/* ДОБАВИТЬ startTime='02.02.2025 12:12', duration='45' -- ДОЛЖЕН ВОЙТИ*/
        Integer taskAdd1 = taskManager.addNewTask(new Task("Задача добавленная 1", "02.02.2025 12:12", 45, "Добавили задачу 1", Status.NEW));
        boolean taskAdd1Contains = taskManager.getPrioritizedTasks().contains(taskManager.getTaskByID(taskAdd1));
        assertTrue(taskAdd1Contains, "Задача №1 не добавилась");

/* ДОБАВИТЬ startTime='03.02.2025 12:12', duration='60*24*30' -- НЕ ДОЛЖЕН ВОЙТИ*/
        Integer taskAdd2 = taskManager.addNewTask(new Task("Задача добавленная 2", "03.02.2025 12:12", 60*24*30, "Добавили задачу 1", Status.NEW));
        assertNull(taskAdd2, "Задача №2 добавилась");

/* УДАЛИТЬ ДОБАВЛЕННЫЕ*/
        if (taskAdd1 != null) taskManager.deleteTask(taskAdd1);
        if (taskAdd2 != null) taskManager.deleteTask(taskAdd2);

    }


}