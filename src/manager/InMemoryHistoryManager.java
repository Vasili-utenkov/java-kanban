package manager;

import tasks.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> historyManager = new HashMap<>();
    private Node head;
    private Node tail;

    void linkLast(Task task) {
        Node node;
        if (tail == null) { // первая нода
            node = new Node(task, null, null); // Node(Task task, Node prev, Node next)
            head = node;
        } else { // Не первая нода
            Node previousNode = tail;
            node = new Node(task, previousNode, null); // Node(Task task, Node prev, Node next)
            previousNode.next = node;
        }
        tail = node;
        historyManager.put(task.getID(), node);
    }

    private void clear(){

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
