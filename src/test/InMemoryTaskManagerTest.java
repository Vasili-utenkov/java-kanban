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

    //    ���������, ��� ���������� ������ Task ����� ���� �����, ���� ����� �� id;
    @Test
    void equalsTaskByID() {
        int taskID = taskManager.addNewTask(new Task("������ ��� �����", "�������� ������ ��� �����", Status.NEW));
        Task taskToCompare1 = taskManager.getTaskByID(taskID);
        Task taskToCompare2 = taskManager.getTaskByID(taskID);
        assertNotNull(taskToCompare1, "1 ��������� ������ Task ��� ��������� ��������� �� ����������");
        assertNotNull(taskToCompare2, "2 ��������� ������ Task ��� ��������� ��������� �� ����������");
        assertEquals(taskToCompare1, taskToCompare2, "���������� ������ Task �� ����� ���� �����, ���� ����� �� id");
    }

    //    ���������, ��� ���������� ������ Task ����� ���� �����, ���� ����� �� id;
    @Test
    void equalsSubTaskByID() {
        int epicID = taskManager.addNewEpic(new Epic("����", "�������� ����"));
        int subTaskID = taskManager.addNewSubTask(new SubTask("��������� ��� �����", "�������� ��������� ��� �����", epicID, Status.NEW));
        SubTask taskToCompare1 = taskManager.getSubTaskByID(subTaskID);
        SubTask taskToCompare2 = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(taskToCompare1, "1 ��������� ������ SubTask ��� ��������� ��������� �� ����������");
        assertNotNull(taskToCompare2, "2 ��������� ������ SubTask ��� ��������� ��������� �� ����������");
        assertEquals(taskToCompare1, taskToCompare2, "���������� ������ SubTask �� ����� ���� �����, ���� ����� �� id");
    }

    @Test
    void equalsEpicByID() {
        int epicID = taskManager.addNewEpic(new Epic("���� ��� �����", "�������� ����"));
        Epic taskToCompare1 = taskManager.getEpicByID(epicID);
        Epic taskToCompare2 = taskManager.getEpicByID(epicID);
        assertNotNull(taskToCompare1, "1 ��������� ������ Epic ��� ��������� ��������� �� ����������");
        assertNotNull(taskToCompare2, "2 ��������� ������ Epic ��� ��������� ��������� �� ����������");
        assertEquals(taskToCompare1, taskToCompare2, "���������� ������ Epic �� ����� ���� �����, ���� ����� �� id");
    }

    //    ���������, ��� ������ Epic ������ �������� � ������ ���� � ���� ���������;
    //  ����������� �� Alexey Monakhov: ���������� �������, ����������
    // ������ ������ ����������� ��� ����� � ������ ����� �������� �����

//    ���������, ��� ������ Subtask ������ ������� ����� �� ������;
//  ����������� �� Alexey Monakhov: ���������� �������, ����������


    //    ���������, ��� ����������� ����� ������ ���������� ��������������������� � ������� � ������ ���������� ����������;
    @Test
    void isManagersIsExists() {
        assertNotNull(taskManager, "taskManager �� ����������");
        assertNotNull(historyManager, "historyManager �� ����������");
    }

    //    ���������, ��� InMemoryTaskManager ������������� ��������� ������ ������� ���� � ����� ����� �� �� id;
    @Test
    void isTaskManagerAddAndFindTasks() {
        int taskID = taskManager.addNewTask(new Task("������ ��� �����", "�������� ������ ��� �����", Status.NEW));
        assertEquals(1, taskManager.getTasksList().size(), "�������� ���������� Task.");
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task �� ������ �� id");

        int epicID = taskManager.addNewEpic(new Epic("���� ��� �����", "�������� ���� ��� �����"));
        assertEquals(1, taskManager.getEpicsList().size(), "�������� ���������� Epic.");
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic �� ������ �� id");

        int subTaskID = taskManager.addNewSubTask(new SubTask("��������� ��� �����", "�������� ��������� ��� �����", epicID, Status.NEW));
        assertEquals(1, taskManager.getSubTasksList().size(), "�������� ���������� Epic.");
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic �� ������ �� id");
    }


//    ���������, ��� ������ � �������� id � ��������������� id �� ����������� ������ ���������;
// ?? ������ ��� ��������� ���� id ��� ������������ ������

    //    �������� ����, � ������� ����������� ������������ ������ (�� ���� �����) ��� ���������� ������ � ��������
    @Test
    void isEqualSavedTasks() {
        String taskName = "������";
        String taskDescription = "�������� ������";
        String epicName = "����";
        String epicDescription = "�������� �����";
        String subTaskName = "���������";
        String subTaskDescription = "�������� ���������";

        int taskID = taskManager.addNewTask(new Task(taskName, taskDescription, Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        assertNotNull(task, "Task �� ������ �� id");
        assertEquals(taskName,task.getTaskName(), "�������� ����� �� ���������.");
        assertEquals(taskDescription,task.getTaskDescription(), "�������� ����� �� ���������.");

        int epicID = taskManager.addNewEpic(new Epic(epicName, epicDescription));
        Epic epic = taskManager.getEpicByID(epicID);
        assertNotNull(epic, "Epic �� ������ �� id");
        assertEquals(epicName,epic.getTaskName(), "�������� ������ �� ���������.");
        assertEquals(epicDescription,epic.getTaskDescription(), "�������� ������ �� ���������.");

        int subTaskID = taskManager.addNewSubTask(new SubTask(subTaskName, subTaskDescription, epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        assertNotNull(subTask, "Epic �� ������ �� id");
        assertEquals(subTaskName,subTask.getTaskName(), "�������� �������� �� ���������.");
        assertEquals(subTaskDescription,subTask.getTaskDescription(), "�������� �������� �� ���������.");
    }


//    ���������, ��� ������, ����������� � HistoryManager, ��������� ���������� ������ ������ � � ������.
    //  ����������� �� Alexey Monakhov: � ������� ������ ������� ������ �� ������, �.�. history manager �� ������ ������ ���������

    @Test
    void isEqualSavedInHistoryTasks() {
        int taskID = taskManager.addNewTask(new Task("������", "�������� ������", Status.NEW));
        Task task = taskManager.getTaskByID(taskID);
        int size = taskManager.getHistory().size();
        Task taskInHistory = taskManager.getHistory().get(size-1);

        assertNotNull(task, "Task �� ������ �� id");
        assertNotNull(taskInHistory, "Task �� ������ � �������");
        assertEquals(task.getTaskName(),taskInHistory.getTaskName(), "�������� ����� �� ���������.");
        assertEquals(task.getTaskDescription(),taskInHistory.getTaskDescription(), "�������� ����� �� ���������.");
        assertEquals(task.getStatus(),taskInHistory.getStatus(), "������� ����� �� ���������.");

        int epicID = taskManager.addNewEpic(new Epic("����", "�������� �����"));
        Epic epic = taskManager.getEpicByID(epicID);
        size = taskManager.getHistory().size();
        Epic epicInHistory = (Epic) taskManager.getHistory().get(size-1);

        assertNotNull(epic, "Epic �� ������ �� id");
        assertNotNull(epicInHistory, "Epic �� ������ � �������");
        assertEquals(epic.getTaskName(),epicInHistory.getTaskName(), "�������� ����� �� ���������.");
        assertEquals(epic.getTaskDescription(),epicInHistory.getTaskDescription(), "�������� ����� �� ���������.");
        assertEquals(epic.getStatus(),epicInHistory.getStatus(), "������� ����� �� ���������.");


        int subTaskID = taskManager.addNewSubTask(new SubTask("���������", "�������� ���������", epicID, Status.NEW));
        SubTask subTask = taskManager.getSubTaskByID(subTaskID);
        size = taskManager.getHistory().size();
        SubTask subTaskInHistory = (SubTask) taskManager.getHistory().get(size-1);

        assertNotNull(subTask, "Task �� ������ �� id");
        assertNotNull(taskInHistory, "Task �� ������ � �������");
        assertEquals(subTask.getTaskName(),subTaskInHistory.getTaskName(), "�������� �������� �� ���������.");
        assertEquals(subTask.getTaskDescription(),subTaskInHistory.getTaskDescription(), "�������� �������� �� ���������.");
        assertEquals(subTask.getStatus(),subTaskInHistory.getStatus(), "������� �������� �� ���������.");

        assertEquals(epic.getEpicSubtasks(),epicInHistory.getEpicSubtasks(), "������ ������� � ������ �� ���������.");
    }

}