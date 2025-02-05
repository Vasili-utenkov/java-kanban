package manager;

import tasks.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, Node> historyManager = new HashMap<>();
    private Node head;
    private Node tail;

    void linkNodeAtEnd(Task task) {
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


    @Override
    public void addTask(Task task) {
        remove(task.getID());
        linkNodeAtEnd(task);
    }

    @Override
    public void remove(int id) {
        Node node, prevNode, nextNode;

        node = head;
        while (node != null) {
            prevNode = node.prev;
            nextNode = node.next;
            if (node.task.getID() == id) {
                node.next = node.prev = null;
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
                historyManager.remove(id);
            }
            node = nextNode;
        }
    }

    @Override
    public List<Task> getHistory() {
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

    private class Node {
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
