import cLass.*;
import manager.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("�������!");

        TaskManager taskManager = Managers.getDefault();

        taskManager.addNewTask(new Task("������ 1", "�������� ������ 1", Status.NEW));
        taskManager.addNewTask(new Task("������ 2", "�������� ������ 2", Status.NEW));

        System.out.println(taskManager.getTasksList());

        taskManager.addNewEpic(new Epic("���� 1", "�������� ���� 1"));
        taskManager.addNewEpic(new Epic("���� 2", "�������� ���� 2"));

//        System.out.println(taskManager.getEpicsList());

        taskManager.addNewSubTask(new SubTask("��������� 1", "�������� ��������� 1", 4, Status.NEW));
        taskManager.addNewSubTask(new SubTask("��������� 2", "�������� ��������� 2", 4, Status.NEW));
        taskManager.addNewSubTask(new SubTask("��������� 3", "�������� ��������� 3", 3, Status.NEW));


        System.out.println(taskManager.getSubTasksList());
        System.out.println(taskManager.getEpicsList());
//        inMemoryTaskManager.setSubTaskStatus(5, Status.DONE);
//        System.out.println(taskManager.getEpicsList());

        taskManager.deleteSubTask(5);
        System.out.println(taskManager.getSubTasksList());
        System.out.println(taskManager.getEpicsList());

        taskManager.getEpicByID(4);
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);
        taskManager.getTaskByID(1);
        Task task = taskManager.getTaskByID(2);

        System.out.println(task);

        taskManager.getSubTaskByID(5);
        taskManager.getSubTaskByID(6);
        taskManager.getSubTaskByID(7);

        System.out.println(taskManager.getHistory());

        taskManager.getTaskByID(1);
        taskManager.getEpicByID(4);

        System.out.println(taskManager.getHistory());

//        printAllTasks(taskManager);
    }

}


