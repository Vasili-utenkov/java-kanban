package manager;

import cLass.*;
import java.util.List;

public interface TaskManager {
    // A. ��������� ������ ���� �����.
    List<Task> getTasksList();

    List<SubTask> getSubTasksList();

    List<Epic> getEpicsList();

    // B. �������� ���� �����.
    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpics();

    // c. ��������� �� ��������������.
    Task getTaskByID(int taskID);

    SubTask getSubTaskByID(int subTaskID);

    Epic getEpicByID(int epicID);

    // d. ��������.��� ������ ������ ������������ � �������� ���������.
    int addNewTask(Task task);

    int addNewSubTask(SubTask subTask);

    int addNewEpic(Epic epic);

    // e. ����������. ����� ������ ������� � ������ ��������������� ��������� � ���� ���������.
    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    // f. �������� �� ��������������.
    void deleteTask(int taskID);

    void deleteSubTask(int subTaskID);

    void deleteEpic(int epicID);

    // a. ��������� ������ ���� �������� ������������ �����.
    List<SubTask> getSubTaskList(int epicID);

    /* ��������� �������� */
    void setTaskStatus(int taskID, Status newStatus);

    void setSubTaskStatus(int subTaskID, Status newStatus);

    void setEpicStatus(int epicID);

    /* ������� ���������� ����� */
    List<Task> getHistory();


}
