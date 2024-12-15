package ru.yandex.javacource.korolyov.taskmanager.manager;

import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;
    private final Map<Integer, Node> history = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return getHistor();
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        final int id = task.getId();
        remove(id);
        linkLast(task);
        history.put(id, tail);
    }

    @Override
    public void remove(int id) {
        final Node node = history.remove(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }

    public boolean isEmpty() {
        return head == null;
    }

    public ArrayList<Task> getHistor() {
        Node element = head;
        ArrayList<Task> ret = new ArrayList<>();
        while (element != null) {
            ret.add(element.data);
            element = element.next;
        }
        return ret;
    }

    private void linkLast(Task task) {
        final Node node = new Node(task, tail, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (node.next == null) {
                tail = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        } else {
            head = node.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        }
    }
}

class Node {

    public Task data;
    public Node next;
    public Node prev;

    public Node(Task data, Node prev, Node next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
