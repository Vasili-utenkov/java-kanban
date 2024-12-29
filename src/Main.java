import Class.*;
import Methods.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        DataMethods dataMethods = new DataMethods();

        dataMethods.addNewTask(new Task("Задача 1", "Добавили задачу 1", Status.NEW));
        dataMethods.addNewTask(new Task("Задача 2", "Добавили задачу 2", Status.NEW));

        System.out.println(dataMethods.getTasksList());

        dataMethods.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        dataMethods.addNewEpic(new Epic("Эпик 2", "Добавили Эпик 2"));

        System.out.println(dataMethods.getEpicList());

        dataMethods.addNewSubTask(new SubTask("ПодЗадача 1", "Добавили ПодЗадача 1", 4, Status.NEW));
        dataMethods.addNewSubTask(new SubTask("ПодЗадача 2", "Добавили ПодЗадача 2", 4, Status.NEW));
        dataMethods.addNewSubTask(new SubTask("ПодЗадача 3", "Добавили ПодЗадача 3",3, Status.NEW));



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


