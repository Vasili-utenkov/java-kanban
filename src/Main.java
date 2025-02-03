import cLass.*;
import manager.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        taskManager.addNewTask(new Task("Задача 1", "Добавили задачу 1", Status.NEW));
        taskManager.addNewTask(new Task("Задача 2", "Добавили задачу 2", Status.NEW));

        System.out.println(taskManager.getTasksList());

        taskManager.addNewEpic(new Epic("Эпик 1", "Добавили Эпик 1"));
        taskManager.addNewEpic(new Epic("Эпик 2", "Добавили Эпик 2"));

//        System.out.println(taskManager.getEpicsList());

        taskManager.addNewSubTask(new SubTask("ПодЗадача 1", "Добавили ПодЗадача 1", 4, Status.NEW));
        taskManager.addNewSubTask(new SubTask("ПодЗадача 2", "Добавили ПодЗадача 2", 4, Status.NEW));
        taskManager.addNewSubTask(new SubTask("ПодЗадача 3", "Добавили ПодЗадача 3", 3, Status.NEW));


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


