package manager;

import tasks.*;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int counter = 0;

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasksList()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getEpicsList()) {
            System.out.println(epic);

            for (SubTask subtask : manager.getSubTaskList(epic.getID())) {
                System.out.println("--> " + subtask);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTasksList()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

    // A. Получение списка всех задач.
    @Override
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(epics.values());
    }

    // B. Удаление всех задач.
    @Override
    public void deleteAllTasks() {
        List<Integer> keys = new ArrayList<>(tasks.keySet());
        for (Integer key : keys) {
            deleteTask(key);
        }
    }

    @Override
    public void deleteAllSubTasks() {
        List<Integer> keys = new ArrayList<>(subTasks.keySet());
        for (Integer key : keys) {
            deleteSubTask(key);
        }
    }

    @Override
    public void deleteAllEpics() { // FIX
        List<Integer> keys = new ArrayList<>(epics.keySet());
        for (Integer key : keys) {
            deleteEpic(key);
        }
    }

    // c. Получение по идентификатору.
    @Override
    public Task getTaskByID(int taskID) {
        Task task = tasks.get(taskID);
        if (task != null) {
            historyManager.addTask(task);
        }
        return task;
    }

    @Override
    public SubTask getSubTaskByID(int subTaskID) {
        SubTask subTask = subTasks.get(subTaskID);
        if (subTask != null) {
            historyManager.addTask(subTask);
        }
        return subTask;
    }

    @Override
    public Epic getEpicByID(int epicID) {
        Epic epic = epics.get(epicID);
        if (epic != null) {
            historyManager.addTask(epic);
        }
        return epic;
    }


// Пересчет значения counter для случает закачки сохраненных задач
    private int getCounter(Integer id) {
        if (id == null) {
            id = ++counter;
        } else {
            if (id > counter) {
                counter = id;
            }
        }
        return id;
    }


    // d. Создание.Сам объект должен передаваться в качестве параметра.
    @Override
    public int addNewTask(Task task) {
        int taskID = getCounter(task.getID());
        task.setID(taskID);
        tasks.put(taskID, task);
        return taskID;
    }

    @Override
    public int addNewSubTask(SubTask subTask) {
        int epicID = subTask.getEpicID();
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return -1;
        }

        int subTaskID = getCounter(subTask.getID());
        subTask.setID(subTaskID);
        epic.addSubTaskID(subTaskID);
        subTasks.put(subTaskID, subTask);
        setEpicStatus(epicID);
        return subTaskID;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int epicID = getCounter(epic.getID());
        epic.setID(epicID);
        epics.put(epicID, epic);
        return epicID;
    }

    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateTask(Task task) {
        int taskID = task.getID();
        tasks.replace(taskID, task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {

        int subTaskID = subTask.getID();
        SubTask subTaskSaved = subTasks.get(subTaskID);
        if (subTaskSaved == null) {
            System.out.println("Подзадачи с кодом " + subTaskID + " не существует.");
            return;
        }
        int epicIDSaved = subTaskSaved.getEpicID();
        int epicIDForUpdate = subTask.getEpicID();
        Epic epicForUpdate = epics.get(epicIDForUpdate);
        if (epicForUpdate == null) {
            System.out.println("Эпика с кодом " + epicIDForUpdate + " не существует.");
            return;
        }

        if (epicIDSaved != epicIDForUpdate) {
            // удалить из старого эпика, записать в новый
            Epic epicSaved = epics.get(epicIDSaved);
            epicSaved.deleteSubTaskID(subTaskID);
            epicForUpdate.addSubTaskID(subTaskID);

            subTasks.replace(subTaskID, subTask);
            setEpicStatus(epicIDSaved);
            setEpicStatus(epicIDForUpdate);

        } else {
            // обновить
            subTasks.replace(subTaskID, subTask);
            setEpicStatus(subTask.getEpicID());
        }


    }

    @Override
    public void updateEpic(Epic epic) {
        int epicID = epic.getID();
        Epic epicForUpdate = epics.get(epicID);
        if (epicForUpdate == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return;
        }
        epicForUpdate.setTaskName(epic.getTaskName());
        epicForUpdate.setTaskDescription(epic.getTaskDescription());
    }

    // f. Удаление по идентификатору.
    @Override
    public void deleteTask(int taskID) {
        Task task = tasks.get(taskID);
        if (task == null) {
            System.out.println("Задачи с кодом " + taskID + " не существует.");
            return;
        }

        tasks.remove(taskID);
        historyManager.remove(taskID);
    }

    @Override
    public void deleteSubTask(int subTaskID) { // FIX
        SubTask subTask = subTasks.get(subTaskID);
        if (subTask == null) {
            System.out.println("Подзадачи с кодом " + subTaskID + " не существует.");
            return;
        }

        int epicID = subTask.getEpicID();
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return;
        }

        epic.deleteSubTaskID(subTaskID);
        subTasks.remove(subTaskID);
        historyManager.remove(subTaskID);
        setEpicStatus(epicID);
    }

    @Override
    public void deleteEpic(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return;
        }

        for (Integer subTaskID : epic.getEpicSubtasks()) {
            subTasks.remove(subTaskID);
            historyManager.remove(subTaskID);
        }

        epics.remove(epicID);
        historyManager.remove(epicID);
    }

    // a. Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<SubTask> getSubTaskList(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return new ArrayList<>();
        }

        ArrayList<SubTask> subTasksList = new ArrayList<>();
        ArrayList<Integer> subTaskListID = epic.getEpicSubtasks();
        for (Integer integer : subTaskListID) {
            subTasksList.add(subTasks.get(integer));
        }

        return subTasksList;
    }

    /* Изменения статусов */
    @Override
    public void setTaskStatus(int taskID, Status newStatus) { // FIX
        tasks.get(taskID).setStatus(newStatus);
    }

    @Override
    public void setSubTaskStatus(int subTaskID, Status newStatus) { // FIX
        SubTask subTask = subTasks.get(subTaskID);
        subTask.setStatus(newStatus);
        setEpicStatus(subTask.getEpicID());
    }

    @Override
    public void setEpicStatus(int epicID) {
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return;
        }

        epic.setStatus(calcEpicStatus(epicID));
    }

    private Status calcEpicStatus(int epicID) {
        Status status, statusCompare;
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return Status.NEW;
        }
        ArrayList<Integer> subTaskListID = epic.getEpicSubtasks();

        if (subTaskListID.isEmpty()) {
            return Status.NEW;
        }
        status = subTasks.get(subTaskListID.get(0)).getStatus();
        if (status == Status.IN_PROGRESS) {
            return Status.IN_PROGRESS;
        }
        for (int i = 1; i < subTaskListID.size(); i++) {
            statusCompare = subTasks.get(subTaskListID.get(i)).getStatus();
            if (statusCompare == Status.IN_PROGRESS) {
                return Status.IN_PROGRESS;
            }
            if (status != statusCompare) {
                return Status.IN_PROGRESS;
            }
        }
        return status;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = historyManager.getHistory();
        return history;
    }


}
