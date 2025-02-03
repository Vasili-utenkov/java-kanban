package manager;

import Class.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int counter = 0;

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();

    // A. ��������� ������ ���� �����.
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


    // B. �������� ���� �����.
    @Override
    public void deleteAllTasks() {
        tasks.clear();
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
        epics.clear();
        subTasks.clear();
    }


    // c. ��������� �� ��������������.
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


    // d. ��������.��� ������ ������ ������������ � �������� ���������.
    @Override
    public int addNewTask(Task task) {
        final int taskID = ++counter;
        task.setID(taskID);
        tasks.put(taskID, task);
        return taskID;
    }

    @Override
    public int addNewSubTask(SubTask subTask) {
        int epicID = subTask.getEpicID();
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return -1;
        }

        final int subTaskID = ++counter;
        subTask.setID(subTaskID);
        epic.addSubTaskID(subTaskID);

        subTasks.put(subTaskID, subTask);
        setEpicStatus(epicID);
        return subTaskID;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int epicID = ++counter;
        epic.setID(epicID);
        epics.put(epicID, epic);
        return epicID;
    }


    // e. ����������. ����� ������ ������� � ������ ��������������� ��������� � ���� ���������.
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

        if (epicIDSaved != epicIDForUpdate) {
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

    @Override
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
    @Override
    public void deleteTask(int taskID) {
        Task task = tasks.get(taskID);
        if (task == null) {
            System.out.println("������ � ����� " + taskID + " �� ����������.");
            return;
        }

        tasks.remove(taskID);
    }

    @Override
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

    @Override
    public void deleteEpic(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return;
        }

        ArrayList<Integer> subTaskListID = epic.getEpicSubtasks();
        for (Integer subTaskID : subTaskListID) {
            subTasks.remove(subTaskID);
        }

        epics.remove(epicID);
    }


    // a. ��������� ������ ���� �������� ������������ �����.
    @Override
    public ArrayList<SubTask> getSubTaskList(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("����� � ����� " + epicID + " �� ����������.");
            return new ArrayList<>();
        }

        ArrayList<SubTask> subTasksList = new ArrayList<>();
        ArrayList<Integer> subTaskListID = epic.getEpicSubtasks();
        for (Integer integer : subTaskListID) {
            subTasksList.add(subTasks.get(integer));
        }

        return subTasksList;
    }


    /* ��������� �������� */
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


    private static void printAllTasks(TaskManager manager) {
        System.out.println("������:");
        for (Task task : manager.getTasksList()) {
            System.out.println(task);
        }
        System.out.println("�����:");
        for (Epic epic : manager.getEpicsList()) {
            System.out.println(epic);

            for (SubTask subtask : manager.getSubTaskList(epic.getID())) {
                System.out.println("--> " + subtask);
            }
        }
        System.out.println("���������:");
        for (Task subtask : manager.getSubTasksList()) {
            System.out.println(subtask);
        }

        System.out.println("�������:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }


}
