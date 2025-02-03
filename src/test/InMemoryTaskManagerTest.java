package test;

import Manager.HistoryManager;
import Manager.Managers;
import Manager.TaskManager;
import org.junit.jupiter.api.Test;
import Class.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private final TaskManager taskManager = Managers.getDefault();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    //    проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void equalsTaskByID() {
        int taskID = taskManager.addNewTask(new Task("Задача для теста", "Добавили задачу для теста", Status.NEW));
        Task taskToCompare1 = taskManager.getTaskByID(taskID);
        Task taskToCompare2 = taskManager.getTaskByID(taskID);
        assertNotNull(taskToCompare1, "1 экземпляр класса Task для сравнения равенства не существует");
        assertNotNull(taskToCompare2, "2 экземпляр класса Task для сравнения равенства не существует");
        assertEquals(taskToCompare1, taskToCompare2, "экземпляры класса Task не равны друг другу, если равен их id");
    }

    //    проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void equalsSubTaskByID() {
        int epicID = taskManager.addNewEpic(new Epic("Эпик", "Добавили Эпик"));
        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача для теста", "Добавили подзадачу для теста", epicID, Status.NEW));
        SubTask taskToCompare1 = taskManager.getSubTaskByID(subTaskID);
        SubTask taskToCompare2 = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(taskToCompare1, "1 экземпляр класса SubTask для сравнения равенства не существует");
        assertNotNull(taskToCompare2, "2 экземпляр класса SubTask для сравнения равенства не существует");
        assertEquals(taskToCompare1, taskToCompare2, "экземпляры класса SubTask не равны друг другу, если равен их id");
    }

    @Test
    void equalsEpicByID() {
        int epicID = taskManager.addNewEpic(new Epic("Эпик для теста", "Добавили Эпик"));
        Epic taskToCompare1 = taskManager.getEpicByID(epicID);
        Epic taskToCompare2 = taskManager.getEpicByID(epicID);
        assertNotNull(taskToCompare1, "1 экземпляр класса Epic для сравнения равенства не существует");
        assertNotNull(taskToCompare2, "2 экземпляр класса Epic для сравнения равенства не существует");
        assertEquals(taskToCompare1, taskToCompare2, "экземпляры класса Epic не равны друг другу, если равен их id");
    }

    //    проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    //  Разъяснения от Alexey Monakhov: невозможно создать, пропускаем
    // Убрать второй конструктор для эпика и второй метод создания эпика

//    проверьте, что объект Subtask нельзя сделать своим же эпиком;
//  Разъяснения от Alexey Monakhov: невозможно создать, пропускаем


    //    убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void isManagersIsExists() {
        assertNotNull(taskManager, "taskManager не существует");
        assertNotNull(historyManager, "historyManager не существует");
    }

    //    проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void isTaskManagerAddAndFindTasks() {
        int taskID = taskManager.addNewTask(new Task("Задача для теста", "Добавили задачу для теста", Status.NEW));
        assertEquals(1, taskManager.getTasksList().size(), "Неверное количество Task.");
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task не найден по id");

        int epicID = taskManager.addNewEpic(new Epic("Эпик для теста", "Добавили эпик для теста"));
        assertEquals(1, taskManager.getEpicsList().size(), "Неверное количество Epic.");
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic не найден по id");

        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача для теста", "Добавили подзадачу для теста", epicID, Status.NEW));
        assertEquals(1, taskManager.getSubTasksList().size(), "Неверное количество Epic.");
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic не найден по id");
    }


//    проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
// ?? Методы без параметра кода id для создаваемого класса

    //    создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void isEqualSavedTasks() {
        String taskName = "Задача";
        String taskDescription = "Описание задачи";
        String epicName = "Эпик";
        String epicDescription = "Описание эпика";
        String subTaskName = "Подзадача";
        String subTaskDescription = "Описание подзадачи";

        int taskID = taskManager.addNewTask(new Task(taskName, taskDescription, Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task не найден по id");
        assertEquals(taskName,task.getTaskName(), "Названия задач не совпадают.");
        assertEquals(taskDescription,task.getTaskDescription(), "Описания задач не совпадают.");

        int epicID = taskManager.addNewEpic(new Epic(epicName, epicDescription));
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic не найден по id");
        assertEquals(epicName,epic.getTaskName(), "Названия эпиков не совпадают.");
        assertEquals(epicDescription,epic.getTaskDescription(), "Описания эпиков не совпадают.");

        int subTaskID = taskManager.addNewSubTask(new SubTask(subTaskName, subTaskDescription, epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic не найден по id");
        assertEquals(subTaskName,subTask.getTaskName(), "Названия подзадач не совпадают.");
        assertEquals(subTaskDescription,subTask.getTaskDescription(), "Описания подзадач не совпадают.");
    }


//    убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    //  Разъяснения от Alexey Monakhov: В истории просто хранить ссылку на задачу, т.е. history manager не должен ничего проверять

    @Test
    void isEqualSavedInHistoryTasks() {
        int taskID = taskManager.addNewTask(new Task("Задача", "Описание задачи", Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        int size = taskManager.getHistory().size();
        Task taskInHistory = taskManager.getHistory().get(size-1);

        assertNotNull(task, "Task не найден по id");
        assertNotNull(taskInHistory, "Task не найден в истории");
        assertEquals(task.getTaskName(),taskInHistory.getTaskName(), "Названия задач не совпадают.");
        assertEquals(task.getTaskDescription(),taskInHistory.getTaskDescription(), "Описания задач не совпадают.");
        assertEquals(task.getStatus(),taskInHistory.getStatus(), "Статусы задач не совпадают.");

        int epicID = taskManager.addNewEpic(new Epic("Эпик", "Описание эпика"));
        Epic epic = taskManager.getEpicByID(epicID);
        size = taskManager.getHistory().size();
        Epic epicInHistory = (Epic) taskManager.getHistory().get(size-1);

        assertNotNull(epic, "Epic не найден по id");
        assertNotNull(epicInHistory, "Epic не найден в истории");
        assertEquals(epic.getTaskName(),epicInHistory.getTaskName(), "Названия задач не совпадают.");
        assertEquals(epic.getTaskDescription(),epicInHistory.getTaskDescription(), "Описания задач не совпадают.");
        assertEquals(epic.getStatus(),epicInHistory.getStatus(), "Статусы задач не совпадают.");


        int subTaskID = taskManager.addNewSubTask(new SubTask("Подзадача", "Описание подзадачи", epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        size = taskManager.getHistory().size();
        SubTask subTaskInHistory = (SubTask) taskManager.getHistory().get(size-1);

        assertNotNull(subTask, "Task не найден по id");
        assertNotNull(taskInHistory, "Task не найден в истории");
        assertEquals(subTask.getTaskName(),subTaskInHistory.getTaskName(), "Названия подзадач не совпадают.");
        assertEquals(subTask.getTaskDescription(),subTaskInHistory.getTaskDescription(), "Описания подзадач не совпадают.");
        assertEquals(subTask.getStatus(),subTaskInHistory.getStatus(), "Статусы подзадач не совпадают.");

        assertEquals(epic.getEpicSubtasks(),epicInHistory.getEpicSubtasks(), "Список позадач у эпиков не совпадают.");
    }

}