package Test;

import Manager.HistoryManager;
import Manager.Managers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Class.*;

import java.util.ArrayList;
import java.util.HashMap;

class InMemoryTaskManagerTest {

    @BeforeAll
    static void BeforeAll() {
        HashMap<Integer, Task> tasks = new HashMap<>();
        HashMap<Integer, SubTask> subTasks = new HashMap<>();
        HashMap<Integer, Epic> epics = new HashMap<>();
        ArrayList<Task> history = new ArrayList<>();
        HistoryManager historyManager = Managers.getDefaultHistory();
    }

    //    ���������, ��� ���������� ������ Task ����� ���� �����, ���� ����� �� id;
    @Test
    void equalsTaskByID() {


    }


    //    ���������, ��� ���������� ������ Task ����� ���� �����, ���� ����� �� id;
    @Test
    void equalsSubTaskByID() {


    }

    @Test
    void equalsEpicByID() {


    }
//    ���������, ��� ������ Epic ������ �������� � ������ ���� � ���� ���������;
//    ���������, ��� ������ Subtask ������ ������� ����� �� ������;
//    ���������, ��� ����������� ����� ������ ���������� ��������������������� � ������� � ������ ���������� ����������;
//    ���������, ��� InMemoryTaskManager ������������� ��������� ������ ������� ���� � ����� ����� �� �� id;
//    ���������, ��� ������ � �������� id � ��������������� id �� ����������� ������ ���������;
//    �������� ����, � ������� ����������� ������������ ������ (�� ���� �����) ��� ���������� ������ � ��������
//    ���������, ��� ������, ����������� � HistoryManager, ��������� ���������� ������ ������ � � ������.

}