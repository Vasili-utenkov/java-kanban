import Class.*;
import Methods.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("�������!");
        DataMethods dataMethods = new DataMethods();

        dataMethods.addNewTask("������ 1", "�������� ������ 1");
        dataMethods.addNewTask("������ 2", "�������� ������ 2");

        System.out.println(dataMethods.getTasksList());

        dataMethods.addNewEpic("���� 1", "�������� ���� 1");
        dataMethods.addNewEpic("���� 2", "�������� ���� 2");


        dataMethods.addNewSubTask("��������� 1", "�������� ��������� 1", 2);
        dataMethods.addNewSubTask("��������� 2", "�������� ��������� 2", 2);
        dataMethods.addNewSubTask("��������� 3", "�������� ��������� 3",3);



        System.out.println(dataMethods.getSubTasksList());
        System.out.println(dataMethods.getEpicList());

// state

        dataMethods.setSubTaskStatus(5, Status.DONE);

        System.out.println(dataMethods.getEpicList());

        dataMethods.deleteSubTask(5);


//        System.out.println(dataMethods.getEpicByID(2).getSubTaskListID());

        System.out.println(dataMethods.getSubTasksList());
        System.out.println(dataMethods.getEpicList());

    }

}


