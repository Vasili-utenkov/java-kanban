package Manager;

import Class.*;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // A. ��������� ������ ���� �����.
    ArrayList<Task> getTasksList();

    ArrayList<SubTask> getSubTasksList();

    ArrayList<Epic> getEpicsList();

    // B. �������� ���� �����.
    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpics();

    // c. ��������� �� ��������������.
    Task getTaskByID(int taskID);

    SubTask getSubTaskByID(int subTaskID);

    Epic getEpicByID(int epicID);

    // d. ��������.��� ������ ������ ������������ � �������� ���������.
    void addNewTask(Task task);

    void addNewSubTask(SubTask subTask);

    void addNewEpic(Epic epic);

    // e. ����������. ����� ������ ������� � ������ ��������������� ��������� � ���� ���������.
    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    // f. �������� �� ��������������.
    void deleteTask(int taskID);

    void deleteSubTask(int subTaskID);

    void deleteEpic(int epicID);

    // a. ��������� ������ ���� �������� ������������ �����.
    ArrayList<SubTask> getSubTaskList(int epicID);

    /* ��������� �������� */
    void setTaskStatus(int taskID, Status newStatus);

    void setSubTaskStatus(int subTaskID, Status newStatus);

    void setEpicStatus(int epicID);

    Status calcEpicStatus(int epicID);

    /* ������� ���������� ����� */
    List<Task> getHistory();


}
