import Class.*;
import Methods.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        DataMethods dataMethods = new DataMethods();

        dataMethods.addNewTask("Задача 1", "Добавили задачу 1");
        dataMethods.addNewTask("Задача 2", "Добавили задачу 2");

        System.out.println(dataMethods.getTasksList());

        dataMethods.addNewEpic("Эпик 1", "Добавили Эпик 1");
        dataMethods.addNewEpic("Эпик 2", "Добавили Эпик 2");


        dataMethods.addNewSubTask("ПодЗадача 1", "Добавили ПодЗадача 1", 2);
        dataMethods.addNewSubTask("ПодЗадача 2", "Добавили ПодЗадача 2", 2);
        dataMethods.addNewSubTask("ПодЗадача 3", "Добавили ПодЗадача 3",3);



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


