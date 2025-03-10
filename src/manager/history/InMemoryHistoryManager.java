package manager.history;

import manager.history.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> historyManager = new HashMap<>();
    private Node head;
    private Node tail;

    private void linkLast(Task task) {
        Node node;
        Node previousNode = tail;
        node = new Node(task, tail, null); // Node(Task task, Node prev, Node next)
        if (previousNode != null) {
            previousNode.next = node;
        } else {
            head = node;
        }
        tail = node;
        historyManager.put(task.getID(), node);
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node node = head;
        if (node != null) {
            while (node != null) {
                tasks.add(node.task);
                node = node.next;
            }
        }
        return tasks;
    }

    @Override
    public void addTask(Task task) {
        remove(task.getID());
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node node, prevNode, nextNode;
        node = historyManager.remove(id);
        if (node != null) {
            prevNode = node.prev;
            nextNode = node.next;
            if (prevNode != null) {
                prevNode.next = nextNode;
            }
            if (nextNode != null) {
                nextNode.prev = prevNode;
            }

            if (node == head) {
                if (nextNode != null) {
                    head = nextNode;
                }
            }
            if (node == tail) {
                if (prevNode != null) {
                    tail = prevNode;
                }
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private static class Node {
        Task task;
        Node prev;
        Node next;

        private Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}
