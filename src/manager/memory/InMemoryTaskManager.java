package manager.memory;

import manager.CompareByStartTime;
import manager.Managers;
import manager.TaskManager;
import manager.history.HistoryManager;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {

    private int counter = 0;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private final Set<Task> sortedTasks = new TreeSet<>(new CompareByStartTime());

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        manager.getTasksList().forEach(System.out::println);

        System.out.println("Эпики:");
        for (Epic epic : manager.getEpicsList()) {
            System.out.println(epic);
            manager.getSubTaskList(epic.getID()).forEach(System.out::println);
        }

        System.out.println("Подзадачи:");
        manager.getSubTasksList().forEach(System.out::println);

        System.out.println("История:");
        manager.getHistory().forEach(System.out::println);
    }

    /* Отсортированный по дате начала, список задач и подзадач */
    @Override
    public Set<Task> getPrioritizedTasks() {
        return sortedTasks;
    }

    // Добавление задачи в список приоритезируемых задач
    private void addTaskInPrioritizedTasks(Task task) {
        if (task.getDuration().isPresent() && task.getStartTime().isPresent()) {
            if (!isInterceptTime(task.getStartTime().get(), task.getDuration().get())) {
                sortedTasks.add(task);
            }
        }
    }

    // Добавление задачи из списка приоритезируемых задач
    private void deleteTaskInPrioritizedTasks(Task task) {
        sortedTasks.remove(task);
    }


    /* Проверка на пересечение */
    public boolean isInterceptTime(LocalDateTime checkingStartTime, Duration checkingDuration) {
        long interceptStart = 0, interceptEnd = 0;
        LocalDateTime checkingEndTime = checkingStartTime.plus(checkingDuration);
/*
даты начала задач после checkingStartTime и перед checkingEndTime
даты окончания задач после checkingStartTime и перед checkingEndTime
*/

        interceptStart = sortedTasks.stream()
                .map(startOfTask -> startOfTask.getStartTime().get())
                .filter(startOfTask -> startOfTask.isAfter(checkingStartTime) && startOfTask.isBefore(checkingEndTime)) //
                .count();

        interceptEnd = sortedTasks.stream()
                .map(endOfTask -> endOfTask.getEndTime().get())
                .filter(endOfTask -> endOfTask.isAfter(checkingStartTime) && endOfTask.isBefore(checkingEndTime)) //
                .count();

        return (interceptEnd > 0) || (interceptStart > 0);
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
        addTaskInPrioritizedTasks(task);
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
        addTaskInPrioritizedTasks(subTask);
        setEpicStatus(epicID);
        setEpicTiming(epicID);

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
        deleteTaskInPrioritizedTasks(task);
        addTaskInPrioritizedTasks(task);
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

            setEpicStatus(epicIDSaved);
            setEpicStatus(epicIDForUpdate);
            setEpicTiming(epicIDSaved);
            setEpicTiming(epicIDForUpdate);

        } else {
            // обновить
            setEpicStatus(subTask.getEpicID());
            setEpicTiming(subTask.getEpicID());
        }

        subTasks.replace(subTaskID, subTask);
        deleteTaskInPrioritizedTasks(subTask);
        addTaskInPrioritizedTasks(subTask);

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
        setEpicTiming(epicID);
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
        deleteTaskInPrioritizedTasks(task);

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
        deleteTaskInPrioritizedTasks(subTask);
        setEpicStatus(epicID);
        setEpicTiming(epicID);
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
            deleteTaskInPrioritizedTasks(getSubTaskByID(subTaskID));
        }

        epics.remove(epicID);
        historyManager.remove(epicID);
    }

    // a. Получение списка всех подзадач определённого эпика.
    @Override
    public List<SubTask> getSubTaskList(int epicID) { // FIX
        Epic epic = epics.get(epicID);
        if (epic == null) {
            System.out.println("Эпика с кодом " + epicID + " не существует.");
            return new ArrayList<>();
        }

        return epic.getEpicSubtasks().stream()
                .map(integer -> subTasks.get(integer))
                .collect(Collectors.toList());
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

    private void setEpicTiming(int epicID) {
        Epic epic = epics.get(epicID);
        Optional<LocalDateTime> minStartDateTime, maxEndDateTime;

        Optional<SubTask> subTask;

        subTask = Optional.of(epic.getEpicSubtasks()
                .stream()
                .map(subTasks::get)
                .filter(ST -> ST.getStartTime().isPresent())
                .min(Comparator.comparing(ST -> ST.getStartTime().get()))
                .get());

        if (subTask.isPresent()) {
            minStartDateTime = subTask.get().getStartTime();
        } else {
            minStartDateTime = Optional.empty();
        }

        subTask = Optional.of(epic.getEpicSubtasks()
                .stream()
                .map(subTasks::get)
                .filter(ST -> ST.getStartTime().isPresent())
                .min(Comparator.comparing(ST -> ST.getEndTime().get()))
                .get());

        if (subTask.isPresent()) {
            maxEndDateTime = subTask.get().getEndTime();
        } else {
            maxEndDateTime = Optional.empty();
        }

        int duration = epic.getEpicSubtasks()
                .stream()
                .map(subTasks::get)
                .map(SubTask::getDuration)
                .filter(Optional::isPresent)
                .mapToInt(i -> (int) i.get().toMinutes())
                .sum();

        epic.setStartTime(minStartDateTime);
        epic.setEndTime(maxEndDateTime);
        epic.setDuration(duration);

    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = historyManager.getHistory();
        return history;
    }

}

