import tasks.*;
import manager.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

//        int task1 = taskManager.addNewTask(new Task("Задача 1", "01.02.2025 11:12", 45, "Добавили задачу 1", Status.NEW));
        Integer task1 = taskManager.addNewTask(new Task("Задача 1", null, null, "Добавили задачу 1", Status.NEW));
        Integer task2 = taskManager.addNewTask(new Task("Задача 1", "01.02.2025 12:12", 45, "Добавили задачу 1", Status.NEW));
//        System.out.println("task1 = " + taskManager.getTaskByID(task1).toString());


        Integer task3 = taskManager.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        Integer task5 = taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "01.03.2025 11:11", 100, "Добавили ПодЗадача 1", task3, Status.NEW));
        Integer task6 = taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "02.03.2025 12:12", 622, "Добавили ПодЗадача 2", task3, Status.NEW));
        Integer task7 = taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "03.03.2025 13:13", 1535, "Добавили ПодЗадача 3", task3, Status.NEW));

//        taskManager.setEpicStartTime(task3);
//        System.out.println("StartTime = " + taskManager.getEpicByID(task3).getStartTime());

        System.out.println("taskManager.getHistory()" + taskManager.getHistory());
        System.out.println();
        System.out.println();
        System.out.println("taskManager.getPrioritizedTasks() = " + taskManager.getPrioritizedTasks());

//        System.out.println("taskManager.getEpicsList() = " + taskManager.getEpicsList());

//        taskManager.getSubTaskList(task3).stream().forEach(System.out::println);

/*

        int task2 = taskManager.addNewTask(new Task("Задача 2", "Добавили задачу 2", Status.NEW));

        int task3 = taskManager.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        int task4 = taskManager.addNewEpic(new Epic("Эпик 2", "Добавили Эпик 2"));

        System.out.println("task3 = " + task3);

        int task5 = taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "Добавили ПодЗадача 1", task3, Status.NEW));
        int task6 = taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "Добавили ПодЗадача 2", task3, Status.NEW));
        int task7 = taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "Добавили ПодЗадача 3", task3, Status.NEW));

// 1.
        taskManager.getTaskByID(task1);
        taskManager.getTaskByID(task2);

//        System.out.println(taskManager.getHistory());

        taskManager.getTaskByID(task1);
//        System.out.println(taskManager.getHistory());

        taskManager.getEpicByID(task4);
        taskManager.getEpicByID(task3);
        taskManager.getTaskByID(task1);
//        System.out.println(taskManager.getHistory());

        //taskManager.deleteTask(1);

        System.out.println(taskManager.getEpicsList());

*/


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


