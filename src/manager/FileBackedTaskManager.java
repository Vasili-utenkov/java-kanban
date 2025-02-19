package manager;

import exception.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File savesTasks;

    public FileBackedTaskManager() {
        savesTasks = new File("Tasks.csv");
        loadTasksFromFile(savesTasks);
    }

    public FileBackedTaskManager(File savesTasks) {
        this.savesTasks = savesTasks;
        loadTasksFromFile(savesTasks);
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void setTaskStatus(int taskID, Status newStatus) {
        super.setTaskStatus(taskID, newStatus);
        save();
    }

    @Override
    public void setSubTaskStatus(int subTaskID, Status newStatus) {
        super.setSubTaskStatus(subTaskID, newStatus);
        save();
    }

    @Override
    public void setEpicStatus(int epicID) {
        super.setEpicStatus(epicID);
        save();
    }

    // Прочитать все
    private void loadTasksFromFile(File savesTasks) {
        String recordOfTask;
        try (BufferedReader reader = new BufferedReader(new FileReader(savesTasks, StandardCharsets.UTF_8))) {
            while ((recordOfTask = reader.readLine()) != null) {
                stringToTask(recordOfTask);
            }
        } catch (FileNotFoundException e) {
            try {
                throw new ManagerSaveException("Нет файла");
            } catch (ManagerSaveException managerSaveException) {
                managerSaveException.printStackTrace();
            }
        } catch (Throwable e) {
            try {
                throw new ManagerSaveException("Что то пошло не так :" + e.getMessage());
            } catch (ManagerSaveException managerSaveException) {
                managerSaveException.printStackTrace();
            }
        }
    }

    // Задача из строки
    private void stringToTask(String stringTask) {

        String[] values = stringTask.split(",");

        if (values[0].equals("id")) {
            return;
        }

        int taskID = Integer.parseInt(values[0]);
        TaskType taskType = TaskType.valueOf(values[1]);
        String taskName = values[2];
        Status status = Status.valueOf(values[3]);
        String taskDescription = values[4];
        int epicID = 0;
        if (taskType.equals(TaskType.SUBTASK)) {
            epicID = Integer.parseInt(values[5]);
        }

        switch (taskType) {
            case TASK -> restoreTask(new Task(taskID, taskName, taskDescription, status));
            case EPIC -> restoreEpic(new Epic(taskID, taskName, taskDescription));
            case SUBTASK -> restoreSubTask(new SubTask(taskID, taskName, taskDescription, epicID, status));
            default -> throw new IllegalStateException("Unexpected value: " + taskType);
        }
    }

    private int restoreTask(Task task) {
        return super.addNewTask(task);
    }

    private int restoreSubTask(SubTask subTask) {
        return super.addNewSubTask(subTask);
    }

    private int restoreEpic(Epic epic) {
        return super.addNewEpic(epic);
    }


    @Override
    public int addNewTask(Task task) {
        int taskID = super.addNewTask(task);
        save();
        return taskID;
    }

    @Override
    public int addNewSubTask(SubTask subTask) {
        int subTaskID = super.addNewSubTask(subTask);
        save();
        return subTaskID;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int epicID = super.addNewEpic(epic);
        save();
        return epicID;
    }

    @Override
    public void deleteTask(int taskID) {
        super.deleteTask(taskID);
        save();
    }

    @Override
    public void deleteSubTask(int subTaskID) {
        super.deleteSubTask(subTaskID);
        save();
    }

    @Override
    public void deleteEpic(int epicID) {
        super.deleteEpic(epicID);
        save();
    }


    // Добавить запись
    private void save() {
// Очистить файл
        if (!savesTasks.delete()) {
            return;
        }
// Создать список строк - задач
        List<String> listOfTasksForSave = createListOfTasksForSave();
// Записать
        try (Writer writer = new FileWriter(savesTasks, StandardCharsets.UTF_8, true)) {

            // 1-я строка - заголовок
            writer.write("id,type,name,status,description,epic");
            writer.write("\n");

            for (String s : listOfTasksForSave) {
                writer.write(s);
                writer.write("\n");
            }
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Что то пошло не так :" + e.getMessage());
            } catch (ManagerSaveException managerSaveException) {
                managerSaveException.printStackTrace();
            }
        }
    }

    // Записать из мапы
    private List<String> createListOfTasksForSave() {
        List<String> listOfTasksForSave = new LinkedList<>();

        for (Task task : getTasksList()) {
            listOfTasksForSave.add(task.taskToSting(task.getID()));
        }

        for (Epic epic : getEpicsList()) {
            listOfTasksForSave.add(epic.taskToSting(epic.getID()));
        }

        for (SubTask subTask : getSubTasksList()) {
            listOfTasksForSave.add(subTask.taskToSting(subTask.getID()));
        }

        return listOfTasksForSave;
    }


}
