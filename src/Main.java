import Class.*;
import Methods.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("�������!");
        DataMethods dataMethods = new DataMethods();

        dataMethods.addNewTask(new Task("������ 1", "�������� ������ 1", Status.NEW));
        dataMethods.addNewTask(new Task("������ 2", "�������� ������ 2", Status.NEW));

        System.out.println(dataMethods.getTasksList());

        dataMethods.addNewEpic(new Epic("���� 1", "�������� ���� 1"));
        dataMethods.addNewEpic(new Epic("���� 2", "�������� ���� 2"));

        System.out.println(dataMethods.getEpicList());

        dataMethods.addNewSubTask(new SubTask("��������� 1", "�������� ��������� 1", 4, Status.NEW));
        dataMethods.addNewSubTask(new SubTask("��������� 2", "�������� ��������� 2", 4, Status.NEW));
        dataMethods.addNewSubTask(new SubTask("��������� 3", "�������� ��������� 3",3, Status.NEW));



        System.out.println(dataMethods.getSubTasksList());
        System.out.println(dataMethods.getEpicList());

// state

//        dataMethods.setSubTaskStatus(5, Status.DONE);

        System.out.println(dataMethods.getEpicList());

        dataMethods.deleteSubTask(5);




        System.out.println(dataMethods.getSubTasksList());
        System.out.println(dataMethods.getEpicList());

    }

}


