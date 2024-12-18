package ru.yandex.javacource.korolyov.taskmanager.manager;

import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

public class Node {

    public Task data;
    public Node next;
    public Node prev;

    public Node(Task data, Node prev, Node next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
