package Methods;

import Class.*;

import java.util.*;

public class DataMethods {

    int counter = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    // A. ��������� ������ ���� �����.
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    // B. �������� ���� �����.
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
        epics.clear();
        subTasks.clear();
    }

    // c. ��������� �� ��������������.
    public Task getTaskByID(int taskID) {
        return tasks.get(taskID);
    }

    public SubTask getSubTaskByID(int subTaskID) {
        return subTasks.get(subTaskID);
    }

    public Epic getEpicByID(int epicID) {
        return epics.get(epicID);
    }

    // d. ��������.��� ������ ������ ������������ � �������� ���������.
    public void addNewTask(Task task) {
        final int taskID = ++counter;
        task.setID(taskID);
        tasks.put(taskID, task);
    }

    public void addNewSubTask(SubTask subTask) {
        int epicID = subTask.getEpicID();
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return;
        }

        final int taskID = ++counter;
        subTask.setID(taskID);
        epic.addSubTaskID(taskID);

        subTasks.put(taskID, subTask);
    }

    public void addNewEpic(Epic epic) {
        final int taskID = ++counter;
        epic.setID(taskID);
        epics.put(taskID, epic);
    }


    // e. ����������. ����� ������ ������� � ������ ��������������� ��������� � ���� ���������.
    public void updateTask(Task task) {
        int taskID = task.getID();
        tasks.replace(taskID, task);
    }

    public void updateSubTask(SubTask subTask) {
        int subTaskID = subTask.getID();
        SubTask subTaskSaved = subTasks.get(subTaskID);
        if (subTaskSaved == null) {
            System.out.println("��������� � ����� " + subTaskID + " �� ����������.");
            return;
        }
        int epicIDSaved = subTaskSaved.getEpicID();
        int epicIDForUpdate = subTask.getEpicID();
        Epic epicForUpdate = epics.get(epicIDForUpdate);
        if (epicForUpdate == null) {
            System.out.println("����� � ����� " + epicIDForUpdate + " �� ����������.");
            return;
        }

        if ( epicIDSaved != epicIDForUpdate) {
            // ������� �� ������� �����, �������� � �����
            Epic epicSaved = epics.get(epicIDSaved);
            epicSaved.deleteSubTaskID(subTaskID);
            epicForUpdate.addSubTaskID(subTaskID);

            subTasks.replace(subTaskID, subTask);
            setEpicStatus(epicIDSaved);
            setEpicStatus(epicIDForUpdate);

        } else {
            // ��������
            subTasks.replace(subTaskID, subTask);
            setEpicStatus(subTask.getEpicID());
        }


    }

    public void updateEpic(Epic epic) {
        int epicID = epic.getID();
        Epic epicForUpdate = epics.get(epicID);
        if (epicForUpdate == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return;
        }
        epicForUpdate.setTaskName(epic.getTaskName());
        epicForUpdate.setTaskDescription(epic.getTaskDescription());
    }


    // f. �������� �� ��������������.
    public void deleteTask(int taskID) {
        Task task = tasks.get(taskID);
        if (task == null) {
            System.out.println("������ � ����� " + taskID + " �� ����������.");
            return;
        }

        tasks.remove(taskID);
    }

    public void deleteSubTask(int subTaskID) { // FIX
        SubTask subTask = subTasks.get(subTaskID);
        if (subTask == null) {
            System.out.println("��������� � ����� " + subTaskID + " �� ����������.");
            return;
        }

        int epicID = subTask.getEpicID();
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return;
        }

        epic.deleteSubTaskID(subTaskID);
        subTasks.remove(subTaskID);
        setEpicStatus(epicID);
    }

    public void deleteEpic(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return;
        }

        ArrayList<Integer> subTaskListID = epic.getSubTaskListID();
        for (Integer subTaskID : subTaskListID) {
            subTasks.remove(subTaskID);
        }

        epics.remove(epicID);
    }

    // a. ��������� ������ ���� �������� ������������ �����.
    public ArrayList<SubTask> getSubTaskList(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return new ArrayList<>();
        }

        ArrayList<SubTask> subTasksList = new ArrayList<>();
        ArrayList<Integer> subTaskListID = epic.getSubTaskListID();
        for (Integer integer : subTaskListID) {
            subTasksList.add(subTasks.get(integer));
        }

        return subTasksList;
    }

    /* ��������� �������� */
    private void setTaskStatus(int taskID, Status newStatus) { // FIX
        tasks.get(taskID).setStatus(newStatus);
    }

    private void setSubTaskStatus(int subTaskID, Status newStatus) { // FIX
        SubTask subTask = subTasks.get(subTaskID);
        subTask.setStatus(newStatus);
        setEpicStatus(subTask.getEpicID());
    }

    private void setEpicStatus(int epicID) {
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return;
        }
        epic.setStatus(calcEpicStatus(epicID));
    }

    private Status calcEpicStatus(int epicID) {
        Status status, statusCompare;
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return Status.NEW;
        }
        ArrayList<Integer> subTaskListID = epic.getSubTaskListID();

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


}
