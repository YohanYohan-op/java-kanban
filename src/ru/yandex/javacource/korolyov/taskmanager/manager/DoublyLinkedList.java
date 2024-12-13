package ru.yandex.javacource.korolyov.taskmanager.manager;

import java.util.ArrayList;

//Мега тяжелая тема, реализовал как смог, Не могу понять - почему не работает, подскажи пожалуйста

public class DoublyLinkedList<T> {
    public Node<T> head;
    public Node<T> tail;
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

    public void add(Node<T> value) {
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
        Node<T> element = tail;
        if (tail.prev == null) {
            head = null;
            size--;
        } else {
            tail.prev.next = null;
            size--;
        }
        tail = tail.prev;
    }

    public ArrayList<Node<T>> list() {
        Node<T> element = head;
        ArrayList<Node<T>> ret = new ArrayList<>();
        while (element != null) {
            ret.add(element);
            element = element.next;
        }
        return ret;
    }

    public ArrayList<T> getHistor() {
        Node<T> element = head;
        ArrayList<T> ret = new ArrayList<>();
        while (element != null) {
            ret.add(element.data);
            element = element.next;
        }
        return ret;
    }

    public void removeNode(Node<T> node) {
        Node<T> element = head;
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