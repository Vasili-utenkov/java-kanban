package Methods;

import Class.*;

import java.util.*;

public class DataMethods {

    int counter = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    // A. ѕолучение списка всех задач.
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    // B. ”даление всех задач.
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

    // c. ѕолучение по идентификатору.
    public Task getTaskByID(int taskID) {
        return tasks.get(taskID);
    }

    public SubTask getSubTaskByID(int subTaskID) {
        return subTasks.get(subTaskID);
    }

    public Epic getEpicByID(int epicID) {
        return epics.get(epicID);
    }

    // d. —оздание.—ам объект должен передаватьс€ в качестве параметра.
    public void addNewTask(Task task) {
        final int taskID = ++counter;
        task.setID(taskID);
        tasks.put(taskID, task);
    }

    public void addNewSubTask(SubTask subTask) {
        final int taskID = ++counter;
        subTask.setID(taskID);
        int epicID = subTask.getEpicID();
        Epic epic = getEpicByID(epicID);
        if (epic == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return;
        }
        epic.addSubTaskID(taskID);

        subTasks.put(taskID, subTask);
    }

    public void addNewEpic(Epic epic) {
        final int taskID = ++counter;
        epic.setID(taskID);
        epics.put(taskID, epic);
    }


    // e. ќбновление. Ќова€ верси€ объекта с верным идентификатором передаЄтс€ в виде параметра.
    public void updateTask(Task task) {
        int taskID = task.getID();
        tasks.replace(taskID, task);
    }

    public void updateSubTask(SubTask subTask) {
        int subTaskID = subTask.getID();
        SubTask subTaskForUpdate = getSubTaskByID(subTaskID);
        if (subTaskForUpdate == null) {
            System.out.println("ѕодзадачи с кодом " + subTaskID + " не существует.");
            return;
        }
        int epicIDForUpdate = subTask.getEpicID();
        Epic epicForUpdate = getEpicByID(epicIDForUpdate);
        if (epicForUpdate == null) {
            System.out.println("Ёпика с кодом " + epicIDForUpdate + " не существует.");
            return;
        }

        subTasks.replace(subTaskID, subTask);
        setEpicStatus(subTask.getEpicID());
    }

    public void updateEpic(Epic epic) {
        int epicID = epic.getID();
        Epic epicForUpdate = getEpicByID(epicID);
        if (epicForUpdate == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return;
        }
        epicForUpdate.setTaskName(epic.getTaskName());
        epicForUpdate.setTaskDescription(epic.getTaskDescription());
    }


    // f. ”даление по идентификатору.
    public void deleteTask(int taskID) {
        Task task = getTaskByID(taskID);
        if (task == null) {
            System.out.println("«адачи с кодом " + taskID + " не существует.");
            return;
        }

        tasks.remove(taskID);
    }

    public void deleteSubTask(int subTaskID) { // FIX
        SubTask subTask = getSubTaskByID(subTaskID);
        if (subTask == null) {
            System.out.println("ѕодзадачи с кодом " + subTaskID + " не существует.");
            return;
        }

        int epicID = subTask.getEpicID();
        Epic epic = getEpicByID(epicID);
        if (epic == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return;
        }

        epic.deleteSubTaskID(subTaskID);
        subTasks.remove(subTaskID);
        setEpicStatus(epicID);
    }

    public void deleteEpic(int epicID) { // FIX
        Epic epic = getEpicByID(epicID);
        if (epic == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return;
        }

        ArrayList<Integer> subTaskListID = epic.getSubTaskListID();
        for (Integer subTaskID : subTaskListID) {
            subTasks.remove(subTaskID);
        }

        epics.remove(epicID);
    }

    // a. ѕолучение списка всех подзадач определЄнного эпика.
    public ArrayList<SubTask> getSubTaskList(int epicID) { // FIX
        Epic epic = getEpicByID(epicID);
        if (epic == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return new ArrayList<>();
        }

        ArrayList<SubTask> subTasksList = new ArrayList<>();
        ArrayList<Integer> subTaskListID = epic.getSubTaskListID();
        for (Integer integer : subTaskListID) {
            subTasksList.add(getSubTaskByID(integer));
        }

        return subTasksList;
    }

    /* »зменени€ статусов */
    private void setTaskStatus(int taskID, Status newStatus) { // FIX
        getTaskByID(taskID).setStatus(newStatus);
    }

    private void setSubTaskStatus(int subTaskID, Status newStatus) { // FIX
        SubTask subTask = getSubTaskByID(subTaskID);
        subTask.setStatus(newStatus);
        setEpicStatus(subTask.getEpicID());
    }

    private void setEpicStatus(int epicID) {
        Epic epic = getEpicByID(epicID);
        if (epic == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return;
        }
        epic.setStatus(calcEpicStatus(epicID));
    }

    private Status calcEpicStatus(int epicID) {
        Status status, statusCompare;
        Epic epic = getEpicByID(epicID);
        if (epic == null) {
            System.out.println("Ёпика с кодом " + epicID + " не существует.");
            return Status.NEW;
        }
        ArrayList<Integer> subTaskListID = epic.getSubTaskListID();

        if (subTaskListID.isEmpty()) {
            return Status.NEW;
        }
        status = getSubTaskByID(subTaskListID.get(0)).getStatus();
        if (status == Status.IN_PROGRESS) {
            return Status.IN_PROGRESS;
        }
        for (int i = 1; i < subTaskListID.size(); i++) {
            statusCompare = getSubTaskByID(subTaskListID.get(i)).getStatus();
            if (statusCompare == Status.IN_PROGRESS) {
                return Status.IN_PROGRESS;
            }
            if (status != statusCompare) {
                return Status.IN_PROGRESS;
            }
        }
        return status;
    }


}
