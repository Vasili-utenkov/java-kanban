package manager.memory;

import manager.TaskManagerTest;
import manager.memory.InMemoryTaskManager;
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


////    проверьте, что наследники класса Task равны друг другу, если равен их id;
//    @DisplayName("SubTask равны друг другу, если равен их id")
//    @Override
//    @Test
//    void equalsSubTaskByID() {
//        int epicID = taskManager.addNewEpic(new Epic("Эпик", "Добавили Эпик"));
//        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача для теста", "01.03.2025 10:10", 1, "Добавили подзадачу для теста", epicID, Status.NEW));
//        SubTask taskToCompare1 = taskManager.getSubTaskByID(subTaskID);
//        SubTask taskToCompare2 = taskManager.getSubTaskByID(subTaskID);
//        assertNotNull(taskToCompare1, "1 экземпляр класса SubTask для сравнения равенства не существует");
//        assertNotNull(taskToCompare2, "2 экземпляр класса SubTask для сравнения равенства не существует");
//        assertEquals(taskToCompare1, taskToCompare2, "экземпляры класса SubTask не равны друг другу, если равен их id");
//    }

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

    //    убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
//    @DisplayName("утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров")
//    @Override
//    @Test
//    void isManagersIsExists() {
//        assertNotNull(taskManager, "taskManager не существует");
//        assertNotNull(historyManager, "historyManager не существует");
//    }

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

        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача для теста", "01.03.2025 10:10", 1, "Добавили подзадачу для теста", epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic не найден по id");

        taskManager.deleteTask(taskID);
        taskManager.deleteEpic(epicID);
        taskManager.deleteSubTask(subTaskID);

    }


    //    создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @DisplayName("неизменность задачи (по всем полям) при добавлении задачи в менеджер")
    @Test
    void isEqualSavedTasks() {
        String taskName = "Задача";
        String taskDescription = "Описание задачи";
        String epicName = "Эпик";
        String startTime = "01.03.2025 10:10";
        int duration = 10;
        String epicDescription = "Описание эпика";
        String subTaskName = "Подзадача";
        String subTaskDescription = "Описание подзадачи";

        int taskID = taskManager.addNewTask(new Task(taskName, startTime, duration, taskDescription, Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task не найден по id");
        assertEquals(taskName, task.getTaskName(), "Названия задач не совпадают.");
        assertEquals(taskDescription, task.getTaskDescription(), "Описания задач не совпадают.");


        int epicID = taskManager.addNewEpic(new Epic(epicName, epicDescription));
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic не найден по id");
        assertEquals(epicName, epic.getTaskName(), "Названия эпиков не совпадают.");
        assertEquals(epicDescription, epic.getTaskDescription(), "Описания эпиков не совпадают.");


        int subTaskID = taskManager.addNewSubTask(new SubTask(subTaskName, startTime, duration, subTaskDescription, epicID, Status.NEW));
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


        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача", "01.03.2025 10:10", 1, "Описание подзадачи", epicID, Status.NEW));
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
        Integer subTask1 = taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "01.03.2025 10:10", 1500, "Добавили ПодЗадача 1", epicID, Status.NEW));
        Integer subTask2 = taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "01.03.2025 10:10", 707, "Добавили ПодЗадача 2", epicID, Status.NEW));
        Integer subTask3 = taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "01.03.2025 10:10", 250, "Добавили ПодЗадача 3", epicID, Status.NEW));

        taskManager.deleteSubTask(subTask1);
        assertFalse(taskManager.getSubTaskList(epicID).contains(subTask1));

        taskManager.deleteSubTask(subTask3);
        assertFalse(taskManager.getSubTaskList(epicID).contains(subTask3));

        taskManager.deleteEpic(epicID);
        taskManager.deleteSubTask(subTask2);
    }


}