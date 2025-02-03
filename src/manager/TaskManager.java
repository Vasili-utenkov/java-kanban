package manager;

import tasks.*;
import java.util.List;

public interface TaskManager {
    // A. Получение списка всех задач.
    List<Task> getTasksList();

    List<SubTask> getSubTasksList();

    List<Epic> getEpicsList();

    // B. Удаление всех задач.
    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpics();

    // c. Получение по идентификатору.
    Task getTaskByID(int taskID);

    SubTask getSubTaskByID(int subTaskID);

    Epic getEpicByID(int epicID);

    // d. Создание.Сам объект должен передаваться в качестве параметра.
    int addNewTask(Task task);

    int addNewSubTask(SubTask subTask);

    int addNewEpic(Epic epic);

    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    // f. Удаление по идентификатору.
    void deleteTask(int taskID);

    void deleteSubTask(int subTaskID);

    void deleteEpic(int epicID);

    // a. Получение списка всех подзадач определённого эпика.
    List<SubTask> getSubTaskList(int epicID);

    /* Изменения статусов */
    void setTaskStatus(int taskID, Status newStatus);

    void setSubTaskStatus(int subTaskID, Status newStatus);

    void setEpicStatus(int epicID);

    /* История просмотров задач */
    List<Task> getHistory();


}
