package Methods;

import Class.*;
import java.util.*;

public class DataMethods {

    static int counter = 0;

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, SubTask> subTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    // A. Получение списка всех задач.
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    // B. Удаление всех задач.
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        List<Integer> keys = new ArrayList<>(subTasks.keySet());
        for (Integer key : keys) {
            deleteSubTask(key);
        }
    }

    public void deleteAllEpics() { // FIX
        List<Integer> keys = new ArrayList<>(epics.keySet());
        for (Integer key : keys) {
            deleteEpic(key);
        }
    }

    // c. Получение по идентификатору.
    public Task getTaskByID(int taskID) {
        return tasks.get(taskID);
    }

    public SubTask getSubTaskByID(int subTaskID) {
        return subTasks.get(subTaskID);
    }

    public Epic getEpicByID(int epicID) {
        return  epics.get(epicID);
    }

    // d. Создание.Сам объект должен передаваться в качестве параметра.
    public void addNewTask(Task task) {
        final int taskID = counter++;
        task.setID(taskID);
        tasks.put(taskID, task);
    }

    public void addNewSubTask(SubTask subTask) {
        final int taskID = counter++;
        subTask.setID(taskID);
        subTasks.put(taskID, subTask);
    }

    public void addNewEpic(Epic epic) {
        final int taskID = counter++;
        epic.setID(taskID);
        epics.put(taskID, epic);
    }

    // d. Создание. ( С параметрами задачи )
    public void addNewTask(String taskName, String taskDescription) {
        Task task = new Task(taskName, taskDescription);
        final int taskID = counter++;
        task.setID(taskID);
        tasks.put(taskID, task);
    }

    public void addNewSubTask(String taskName, String taskDescription, int epicID) {
        SubTask subTask = new SubTask(taskName, taskDescription);
        final int subTaskID = counter++;
        subTask.setID(subTaskID);
        subTask.setEpicID(epicID);
        subTasks.put(subTaskID, subTask);
        getEpicByID(epicID).addSubTaskID(subTaskID);
    }

    public void addNewEpic(String taskName, String taskDescription) {
        Epic epic = new Epic(taskName, taskDescription);
        final int epicID = counter++;
        epic.setID(epicID);
        epics.put(epicID, epic);

    }

    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateTask(Task task) {
        int taskID = task.getID();
        tasks.replace(taskID, task);
    }

    public void updateSubTask(SubTask subTask) {
        int subTaskID = subTask.getID();
        subTasks.replace(subTaskID, subTask);
    }

    public void updateEpic(Epic epic) {
        int epicID = epic.getID();
        tasks.replace(epicID, epic);
    }

    // e. Обновление. ( С параметрами задачи )
    public void updateTask(int taskID, String taskName, String taskDescription) {
        Task task = tasks.get(taskID);
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        tasks.replace(taskID, task);
    }

    public void updateSubTask(int subTaskID, String taskName, String taskDescription) {
        SubTask subTask = subTasks.get(subTaskID);
        subTask.setTaskName(taskName);
        subTask.setTaskDescription(taskDescription);
        tasks.replace(subTaskID, subTask);
    }

    public void updateEpic(int epicID, String taskName, String taskDescription) {
        Epic epic = epics.get(epicID);
        epic.setTaskName(taskName);
        epic.setTaskDescription(taskDescription);
        tasks.replace(epicID, epic);
    }

    // f. Удаление по идентификатору.
    public void deleteTask(int taskID) {
        tasks.remove(taskID);
    }

    public void deleteSubTask(int subTaskID) { // FIX
        int epicID = getSubTaskByID(subTaskID).getEpicID();
        getEpicByID(epicID).deleteSubTaskID(subTaskID);
        subTasks.remove(subTaskID);
        setEpicStatus(epicID);
    }

    public void deleteEpic(int epicID) { // FIX
        ArrayList<Integer> subTaskListID = getSubTaskList(epicID);
        for (Integer subTaskID : subTaskListID) {
            getSubTaskByID(subTaskID).setEpicID(0);
        }
        epics.remove(epicID);
    }

    // a. Получение списка всех подзадач определённого эпика.
    public ArrayList<Integer> getSubTaskList(int epicID) { // FIX
        return getEpicByID(epicID).getSubTaskListID();
    }

    /* Изменения статусов */
    public void setTaskStatus(int taskID, Status newStatus) { // FIX
        getTaskByID(taskID).setStatus(newStatus);
    }

    public void setSubTaskStatus(int subTaskID, Status newStatus) { // FIX
        SubTask subTask = getSubTaskByID(subTaskID);
        subTask.setStatus(newStatus);
        setEpicStatus(subTask.getEpicID());
    }

    public void setEpicStatus(int epicID) {
        getEpicByID(epicID).setStatus(calcEpicStatus(epicID));
    }

    public Status calcEpicStatus(int epicID) {
        Status status, statusCompare;
        ArrayList<Integer> subTaskListID = getEpicByID(epicID).getSubTaskListID();

        if (subTaskListID.isEmpty()) {
            return Status.NEW;
        }
        status = getSubTaskByID(subTaskListID.get(0)).getSubTaskStatus();

        for (int i = 1; i < subTaskListID.size(); i++) {
            statusCompare = getSubTaskByID(subTaskListID.get(i)).getSubTaskStatus();
            if (status != statusCompare) {
                return Status.IN_PROGRESS;
            }
        }
        return status;
    }


}
