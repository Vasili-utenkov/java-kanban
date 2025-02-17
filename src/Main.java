import tasks.*;
import manager.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        System.out.println(taskManager.getEpicsList());


        int task1 = taskManager.addNewTask(new Task("Задача 1", "Добавили задачу 1", Status.NEW));
        int task2 = taskManager.addNewTask(new Task("Задача 2", "Добавили задачу 2", Status.NEW));

        int task3 = taskManager.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        int task4 = taskManager.addNewEpic(new Epic("Эпик 2", "Добавили Эпик 2"));

        int task5 = taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "Добавили ПодЗадача 1", task3, Status.NEW));
        int task6 = taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "Добавили ПодЗадача 2", task3, Status.NEW));
        int task7 = taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "Добавили ПодЗадача 3", task3, Status.NEW));

// 1.
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);

//        System.out.println(taskManager.getHistory());

        taskManager.getTaskByID(1);
//        System.out.println(taskManager.getHistory());

        taskManager.getEpicByID(4);
        taskManager.getEpicByID(3);
        taskManager.getTaskByID(1);
//        System.out.println(taskManager.getHistory());

        //taskManager.deleteTask(1);




/*
        taskManager.getEpicByID(4);
        taskManager.getSubTaskByID(5);
        taskManager.getSubTaskByID(6);
        taskManager.getSubTaskByID(7);
        System.out.println(taskManager.getHistory());

        taskManager.deleteSubTask(6);
        System.out.println(taskManager.getHistory());
        taskManager.getEpicByID(4);
        taskManager.getTaskByID(1);
        System.out.println(taskManager.getHistory());

        taskManager.deleteEpic(3);
        System.out.println(taskManager.getHistory());
*/
    }

}


