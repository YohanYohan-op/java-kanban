package ru.yandex.javacource.korolyov.taskmanager.manager;

import java.util.ArrayList;

//Мега тяжелая тема, реализовал как смог, прошу слишком строго не судить:).

public class DoublyLinkedList<Task> {
    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;
    private int schet = 0;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void add(Node<Task> value) {
        if (isEmpty()) {
            head = value;
            size++;
        } else {
            tail.next = value;
            value.prev = tail;
            size++;
        }
        tail = value;
    }

    public void removeLast() {
        Node<Task> element = tail;
        if (tail.prev == null) {
            head = null;
            size--;
        } else {
            tail.prev.next = null;
            size--;
        }
        tail = tail.prev;
    }

    public ArrayList<Node<Task>> list() {
        Node<Task> element = head;
        ArrayList<Node<Task>> ret = new ArrayList<>();
        while (element!=null) {
            ret.add(element);
            element = element.next;
        }
        return ret;
    }

    public ArrayList<Task> getHistor() {
        Node<Task> element = head;
        ArrayList<Task> ret = new ArrayList<>();
        while (element!=null) {
            ret.add(element.data);
            element = element.next;
        }
        return ret;
    }

    public void removeNode(Node<Task> node) {
        Node<Task> element = head;
        if (node == head) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.data = null;
            size--;
        } else {
            while (schet != size) {
                element = element.next;
                if (node == element) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.data = null;
                    size--;
                    break;
                }
                schet++;
            }
        }
    }
}

class Node<Task> {

    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Task data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}